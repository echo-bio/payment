package com.echobio.payment.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import com.echobio.payment.common.exception.BusinessException;
import com.echobio.payment.service.QiufengService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.echobio.payment.constants.QiufengConstant.*;

@Service
@Slf4j
public class QiufengServiceImpl implements QiufengService {

    @Override
    public String submitPayment(Map<String, Object> params) {
        log.info("call qiufeng service with param:{}", params);
        //todo remove below codes
        params.put("money", 1.00);
        params.put("name", "test" + System.currentTimeMillis());
        params.put("type", "wxpay");
        params.put("out_trade_no", System.currentTimeMillis());

        String url = "";
        try {
            params.put("notify_url", "//www.cccyun.cc/notify_url.php");
            params.put("pid", PID);
            params.put("return_url", "http://echo-bio.cn:8088/");
            params.put("sitename", "echo-bio");
            String sign = generateSign(params);
            params.put("sign", sign);
            params.put("sign_type", "MD5");
            url = URL_PREFIX + createLinkStringByGet(params);
            HttpUtil.get(url);
        } catch (Exception e) {
            log.error("error occurs when call qiufeng platform with url {}", url, e);
            throw new BusinessException(String.format("error occurs when call qiufeng platform with url %s", url), e);
        }
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
}
