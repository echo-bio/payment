package com.echobio.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echobio.payment.common.exception.BusinessException;
import com.echobio.payment.dao.po.OrderPO;
import com.echobio.payment.dto.OrderDTO;


import java.math.BigDecimal;

/**
 * 订单业务接口
 *
 * Created by FSQ
 * CopyRight https://www.fuint.cn
 */
public interface OrderService extends IService<OrderPO> {

    /**
     * 创建订单
     *
     * @param  reqDto
     * @throws BusinessException
     */
    OrderPO createOrder(OrderDTO reqDto) throws BusinessException;


    /**
     * 把订单置为已支付
     * @param orderId
     * @param payAmount
     * @return
     * */
    Boolean setOrderPayed(Integer orderId, BigDecimal payAmount) throws BusinessException;
}
