package com.echobio.payment.service;


import java.util.Map;

public interface QiufengService {
    String submitPayment(Map<String, Object> params);

    String generateSign(Map<String, Object> params);
}
