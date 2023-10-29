package com.echobio.payment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echobio.payment.common.exception.BusinessException;
import com.echobio.payment.dao.mapper.OrderMapper;
import com.echobio.payment.dao.po.OrderPO;
import com.echobio.payment.dto.OrderDTO;
import com.echobio.payment.service.OrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

import static com.echobio.payment.constants.QiufengConstant.AMOUNT_REDIS_KEY;

/**
 * 订单接口实现类
 *
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {

    @Autowired
    private Gson gson;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 保存订单信息
     *
     * @param  orderDto
     * @throws BusinessException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderPO createOrder(OrderDTO orderDto) throws BusinessException {
        log.info("start to create order with param{}", gson.toJson(orderDto));
        // create order with orderNo
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderDto, orderPO);
        //generate orderNo
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        orderPO.setOrderNo(String.valueOf(orderNo));
        // calculate order amount ,todo minus point
        // generate random discount for qiufeng todo will be deprecated
        Double payAmount = orderDto.getAmount();
        // update point table if use point
        //todo redis lock the payAmount because qiufeng dont allow same amount exist at the same time
        boolean amountNotPaid = redisTemplate.opsForHash().hasKey(AMOUNT_REDIS_KEY, payAmount);
        if (amountNotPaid){
            double discountAmount;
            do {
                discountAmount = payAmount;
                Random random = new Random();
                double discount = random.nextInt(100)*0.01;
                orderPO.setDiscount(discount);
                discountAmount -= discount;
            } while (discountAmount <=0);
            payAmount = discountAmount;
            amountNotPaid = redisTemplate.opsForHash().hasKey(AMOUNT_REDIS_KEY, payAmount);
            if (amountNotPaid){
                throw new BusinessException("创建订单失败，请稍后再试");
            }
        }
        redisTemplate.opsForHash().put(AMOUNT_REDIS_KEY, payAmount, System.currentTimeMillis());
        orderPO.setPayAmount(payAmount);
        save(orderPO);
        return orderPO;
    }

    @Override
    public Boolean setOrderPayed(Integer orderId, BigDecimal payAmount) throws BusinessException {
        return null;
    }

    @Override
    public Boolean updateByTradeNo(OrderPO orderPO) {
        UpdateWrapper<OrderPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trade_no", orderPO.getOrderNo());
        return update(orderPO, updateWrapper);
    }

    @Override
    public OrderPO getByTradeNo(String outTradeNo) {
        QueryWrapper<OrderPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trade_no", outTradeNo);
        return getOne(queryWrapper);
    }
}
