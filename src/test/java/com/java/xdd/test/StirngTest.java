package com.java.xdd.test;


import com.alibaba.fastjson.JSONObject;
import com.java.xdd.system.domain.SystemLog;
import com.java.xdd.websocket1.domain.Message;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void test3(){
        String payload = "\n\n\naba\n\n\naba\naba\n\n\n\n";
        /*while (payload.startsWith("\n")) {
            payload = payload.substring(1);
        }
        while (payload.endsWith("\n")) {
            payload = payload.substring(0,payload.lastIndexOf("\n"));
        }*/
        String[] tempSplit = payload.split("\n");

        List<String> split = new ArrayList<>();
        for (String s : tempSplit) {
            if (StringUtils.isNotBlank(s)) split.add(s);
        }
        for (String s : split) {
            System.out.println(s);
        }
    }
}
