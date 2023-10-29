package com.echobio.payment.controller.response;


import lombok.Data;

@Data
public class BaseResponse<T> {

    public static final String SUCCESS = "success";

    public BaseResponse() {
    }

    private int code;
    private String msg;
    private T data;

    public static BaseResponse ofSuccess() {
        BaseResponse res = new BaseResponse<>();
        res.code = 200;
        res.msg = SUCCESS;
        return res;
    }

    public static <T> BaseResponse<T> ofSuccess(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = 200;
        response.data = data;
        response.msg = SUCCESS;
        return response;
    }

    public static <T> BaseResponse<T> ofSuccess(T data, String msg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = 200;
        response.data = data;
        response.msg = msg;
        return response;
    }

    public static BaseResponse ofFailed(String msg) {
        BaseResponse response = new BaseResponse<>();
        response.code = 500;
        response.msg = msg;
        return response;
    }

    public static BaseResponse ofFailed(int code, String msg) {
        BaseResponse response = new BaseResponse<>();
        response.code = code;
        response.msg = msg;
        return response;
    }
}
