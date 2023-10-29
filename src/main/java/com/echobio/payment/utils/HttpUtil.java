package com.echobio.payment.utils;

import cn.hutool.http.HttpRequest;

public class HttpUtil {

    public static String post(String url, String param) {
        return HttpRequest.post(url)
                .body(param)
                .execute()
                .body();
    }
}
