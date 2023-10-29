package com.echobio.payment.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echobio.payment.common.exception.BusinessException;
import com.echobio.payment.dao.mapper.OrderMapper;
import com.echobio.payment.dao.po.OrderPO;
import com.echobio.payment.dto.OrderDTO;
import com.echobio.payment.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 订单接口实现类
 *
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderPO> implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 保存订单信息
     *
     * @param  orderDto
     * @throws BusinessException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderPO createOrder(OrderDTO orderDto) throws BusinessException {
        // create order with orderNo
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderDto, orderPO);
        //generate orderNo
        String orderNo = IdUtil.getSnowflakeNextIdStr();
        orderPO.setOrderNo(String.valueOf(orderNo));
        // calculate order amount ,todo minus point
        // generate random discount for qiufeng todo will be deprecated
        Random random = new Random();
        double discount = random.nextInt(100)*0.01;
        orderPO.setDiscount(discount);
        Double payAmount = orderDto.getAmount();
        //minus discount
        payAmount -= discount;
        // update point table if use point
        //redis lock the payAmount because qiufeng dont allow same amount exist at the same time

        orderPO.setPayAmount(payAmount);
        return orderPO;
    }

    @Override
    public Boolean setOrderPayed(Integer orderId, BigDecimal payAmount) throws BusinessException {
        return null;
    }
}
