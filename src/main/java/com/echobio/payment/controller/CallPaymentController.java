package com.echobio.payment.controller;

import com.echobio.payment.controller.request.CallPaymentRequest;
import com.echobio.payment.controller.request.PaymentCallbackRequest;
import com.echobio.payment.service.PaymentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("echobio/payment")
public class CallPaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String callPayment() {
        //check auth
        //handle exception
        val request = new CallPaymentRequest();
        return paymentService.doPay(request);
    }

    @GetMapping(value = "/callback")
    public void callbackPayment(PaymentCallbackRequest request){
        paymentService.callback(request);
    }

//    @GetMapping(value = "Submit/Mym_Pay.php")
//    public HttpServletResponse redirectQiufeng(@RequestParam("trade_no" String tradeNo)) {
//        HttpResource httpResource = new HttpResponse();
//    }
}
