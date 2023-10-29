package com.echobio.payment.service.impl;


import com.echobio.payment.controller.request.CallPaymentRequest;
import com.echobio.payment.controller.request.PaymentCallbackRequest;
import com.echobio.payment.service.PaymentService;
import com.echobio.payment.service.QiufengService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private QiufengService qiufengService;

    @Override
    public String doPay(CallPaymentRequest request) {
        HashMap<String, Object> payParams = new HashMap<>();
//        Map<String, Object> payParams = BeanUtil.beanToMap(request);
        return qiufengService.submitPayment(payParams);
    }

    @Override
    public void callback(PaymentCallbackRequest request) {
        //update order pay_status
        //minus change
    }
}
