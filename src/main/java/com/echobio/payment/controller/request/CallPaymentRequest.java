package com.echobio.payment.controller.request;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class CallPaymentRequest {
    private String type;
    private String name;
    private Double money;
    @Alias("out_trade_no")
    private String outTradeNo;
}
