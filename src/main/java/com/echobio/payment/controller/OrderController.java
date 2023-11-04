package com.echobio.payment.controller;

import com.echobio.payment.controller.request.CreateOrderRequest;
import com.echobio.payment.controller.response.BaseResponse;
import com.echobio.payment.dao.po.OrderPO;
import com.echobio.payment.dto.OrderDTO;
import com.echobio.payment.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("echobio")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    @ApiOperation(value = "create payment order")
    public BaseResponse createOrder(@RequestBody CreateOrderRequest request) {
        //check request
        try {
            OrderPO order = orderService.createOrder(OrderDTO.builder()
                    .type(request.getType())
                    .typeName(request.getTypeName())
                    .amount(request.getAmount())
                    .build());
            return BaseResponse.ofSuccess(order);
        } catch (Exception e) {
            return BaseResponse.ofFailed(e.getMessage());
        }
    }
}
