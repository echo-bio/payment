package com.echobio.payment.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import com.echobio.payment.service.QiufengService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QiufengServiceImpl implements QiufengService {

    public static final String URL_PREFIX = "https://mpay.qfysc.cn/submit.php?";

    public static final int PID = 140453069;

    public static final String KEY = "Ow0HQiPZ2ci";

    @Override
    public String submitPayment(Map<String, Object> params) {
        params.put("money", 1.00);
        params.put("name", "test"+System.currentTimeMillis());
        params.put("type", "wxpay");
        params.put("out_trade_no", System.currentTimeMillis());


        params.put("notify_url", "//www.cccyun.cc/notify_url.php");
        params.put("pid", PID);
        params.put("return_url", "http://echo-bio.cn:8088/");
        params.put("sitename", "echo-bio");
        String sign = generateSign(params);
        params.put("sign", sign);
        params.put("sign_type", "MD5");
        String url = URL_PREFIX + createLinkStringByGet(params);
        String responseHtml = HttpUtil.get(url);
        return responseHtml.replace("Submit", "https://mpay.qfysc.cn/Submit");
    }

    public String generateSign(Map<String, Object> param) {
        String linkStringByGet = createLinkStringByGet(param);
        linkStringByGet += KEY;
        val md5 = MD5.create();
        return md5.digestHex(linkStringByGet);
    }

    public String createLinkStringByGet(Map<String, Object> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);
            if (i == keys.size() - 1) {
                stringBuilder.append(key).append("=").append(value.toString());
            } else {
                stringBuilder.append(key).append("=").append(value.toString()).append("&");
            }

        }
        return stringBuilder.toString();
    }

//    public static void main(String[] args) {
//        Map<String,Object> param = new HashMap<>();
//        param.put("money", "0.01");
//        param.put("name", "生信分析");
//        param.put("notify_url", "\t//www.cccyun.cc/notify_url.php");
//        param.put("out_trade_no", "1234567");
//        param.put("pid", 140453069);
//        param.put("return_url", "//www.cccyun.cc/return_url.php");
//        param.put("sitename", "echo-bio");
//        param.put("type", "wxpay");
//        String sign = generateSign(param);
//        param.put("sign", sign);
//        param.put("sign_type", "MD5");
//        val post = HttpUtil.post(URL_PREFIX, createLinkStringByGet(param));
//        System.out.println(post);
//
//        System.out.println(createLinkStringByGet(param));
//    }
}
