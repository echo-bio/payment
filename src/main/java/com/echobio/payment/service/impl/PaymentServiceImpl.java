package com.echobio.payment.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.echobio.payment.common.exception.BusinessException;
import com.echobio.payment.controller.request.CallPaymentRequest;
import com.echobio.payment.controller.request.PaymentCallbackRequest;
import com.echobio.payment.dao.po.OrderPO;
import com.echobio.payment.enums.CryptoTypeEnum;
import com.echobio.payment.enums.QiufengPayStatusEnum;
import com.echobio.payment.service.OrderService;
import com.echobio.payment.service.PaymentService;
import com.echobio.payment.service.QiufengService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.echobio.payment.constants.QiufengConstant.AMOUNT_REDIS_KEY;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private QiufengService qiufengService;

    @Resource
    private OrderService orderService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private Gson gson;

    @Override
    public String doPay(CallPaymentRequest request) {
        HashMap<String, Object> payParams = new HashMap<>();
//        Map<String, Object> payParams = BeanUtil.beanToMap(request);
        return qiufengService.submitPayment(payParams);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void callback(PaymentCallbackRequest request) {
        log.info("start to handle payment call with params{}", gson.toJson(request));
        //check sign
        Map<String, Object> params = BeanUtil.beanToMap(request);
        //only have md5 crypto type for now
        if (CryptoTypeEnum.MD5.getName().equals(request.getSignType())) {
            String input = qiufengService.generateSign(params);
            if (!input.equals(request.getSign())) {
                //should unlock amount here?
                log.error("tradeNo{} error, sign is wrong", request.getOutTradeNo());
                throw new BusinessException("sign is wrong");
            }
        } else {
            log.error("sign_type {} is wrong", request.getSignType());
            throw new BusinessException(String.format("Sign_type %s is wrong", request.getSignType()));
        }
        //update order
        OrderPO orderPO = orderService.getByTradeNo(request.getOutTradeNo());
        QiufengPayStatusEnum enumByCode = QiufengPayStatusEnum.getEnumByCode(request.getTradeStatus());
        orderPO.setPayStatus(enumByCode.getCode());
        //todo the result may different from the amount submitted before, take consideration
        if (QiufengPayStatusEnum.SUCCESS.equals(enumByCode)) {
            //unlock amount
            redisTemplate.opsForHash().delete(AMOUNT_REDIS_KEY, orderPO.getAmount());
        } else {
            //record fail reason
        }
        orderService.save(orderPO);
    }
}
