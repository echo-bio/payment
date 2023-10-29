package com.echobio.payment.controller.request;

import cn.hutool.core.annotation.Alias;
import cn.hutool.core.annotation.PropIgnore;
import lombok.Data;

@Data
public class PaymentCallbackRequest {
    private Integer pid;
    @Alias("trade_no")
    private String tradeNo;
    @Alias("out_trade_no")
    private String outTradeNo;
    private String type;
    private String name;
    private String money;
    @Alias("trade_status")
    private String tradeStatus;
    @PropIgnore
    private String sign;
    @Alias("sign_type")
    @PropIgnore
    private String signType;
}
