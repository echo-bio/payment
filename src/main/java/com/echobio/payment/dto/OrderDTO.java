package com.echobio.payment.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private String type;
    private String typeName;
    private String orderNo;
    private Integer payStatus;
    private String userId;
    private Double amount;
    private Integer pointUsed;
    private Double pointAmount;
    private Double discount;
    private Date payTime;
}
