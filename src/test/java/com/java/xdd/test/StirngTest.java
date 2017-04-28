package com.java.xdd.test;


import com.alibaba.fastjson.JSONObject;
import com.java.xdd.system.domain.SystemLog;
import com.java.xdd.websocket1.domain.Message;
import freemarker.ext.beans.HashAdapter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

    @Test
    public void test4(){
        String str = "{\"ct\":{\"describe\":\"123\",\"required\":0,\"serviceName\":\"测试1\",\"service_name\":\"测试1\"},\"cts\":[{\"describe\":\"123\",\"required\":0,\"serviceName\":\"测试1\",\"service_name\":\"测试1\"},{\"describe\":\"1234\",\"required\":0,\"serviceName\":\"测试2\",\"service_name\":\"测试2\"}],\"customFileds\":[{\"code\":\"detailed_1\",\"hint\":\"\",\"required\":0,\"showName\":\"1\",\"tag\":\"tag0\",\"value\":\"\"},{\"code\":\"detailed_2\",\"hint\":\"\",\"required\":0,\"showName\":\"2\",\"tag\":\"tag1\",\"value\":\"\"},{\"code\":\"detailed_3\",\"hint\":\"\",\"required\":0,\"showName\":\"3\",\"tag\":\"tag2\",\"value\":\"\"},{\"code\":\"detailed_4\",\"hint\":\"\",\"required\":0,\"showName\":\"4\",\"tag\":\"tag3\",\"value\":\"\"}],\"customSelete\":[{\"code\":\"detailed_0\",\"options\":[\"1\",\"2\",\"3\"],\"required\":0,\"showName\":\"下拉框1\",\"value\":\"\"}],\"fileds\":[{\"code\":\"patient_name\",\"days\":0,\"name\":\"患者姓名\",\"required\":0},{\"code\":\"patient_age\",\"days\":0,\"name\":\"患者年龄\",\"required\":0},{\"code\":\"patient_sex\",\"days\":0,\"name\":\"患者性别\",\"required\":0},{\"code\":\"operation_date\",\"days\":4,\"name\":\"计划手术日期\",\"required\":0}]}";
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");

        String s = JSONObject.toJSONString(map);
        System.out.println(s);
        System.out.println(JSONObject.toJSONString(s));


    }


}
