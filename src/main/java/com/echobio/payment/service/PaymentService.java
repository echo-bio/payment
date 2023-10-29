package com.echobio.payment.service;

import com.echobio.payment.controller.request.CallPaymentRequest;
import com.echobio.payment.controller.request.PaymentCallbackRequest;

public interface PaymentService {

    String doPay(CallPaymentRequest request);

    void callback(PaymentCallbackRequest request);
}
