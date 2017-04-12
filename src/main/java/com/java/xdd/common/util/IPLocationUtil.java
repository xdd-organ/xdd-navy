package com.java.xdd.common.util;

import com.java.xdd.common.constant.AliyunConstant;
import com.java.xdd.common.httpclient.HttpClientUtil;
import com.java.xdd.common.httpclient.HttpResult;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("iPLocationUtil")
public class IPLocationUtil {
    private Logger logger = LoggerFactory.getLogger(IPLocationUtil.class);

    @Autowired
    private HttpClientUtil httpClientUtil;

    public String getIPLocation(String ip) {
        String url = AliyunConstant.QUERY_IP_LOCATION_URL;
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.AUTHORIZATION, "APPCODE " + AliyunConstant.APP_CODE);
        try {
            HttpResult httpResult = httpClientUtil.doGet(url, params, headers);
            return httpResult.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查找IP位置出错->{}", e.getMessage());
        }
        return null;
    }
}
