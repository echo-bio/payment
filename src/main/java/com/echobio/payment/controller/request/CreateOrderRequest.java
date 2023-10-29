package com.echobio.payment.controller.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String type;
    private String typeName;
    private Double amount;
}
