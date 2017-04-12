package com.java.xdd.test;


import com.alibaba.fastjson.JSONObject;
import com.java.xdd.system.domain.SystemLog;
import com.java.xdd.websocket1.domain.Message;
import org.junit.Test;

public class StirngTest {
    @Test
    public void test1(){
        String name = "分割重建方案";
        String temp = name.substring(0,name.length() - 2 );
        System.out.println(temp);
    }
    @Test
    public void test2(){
        String payload = "{\"content\":\"aksjdfkasdnfasdfas\",\"sendTo\":3}";
        Message msg = JSONObject.parseObject(payload, Message.class);
        System.out.println(msg);
    }
}
