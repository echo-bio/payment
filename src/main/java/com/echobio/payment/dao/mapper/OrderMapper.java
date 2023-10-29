package com.echobio.payment.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.echobio.payment.dao.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderPO> {
}
