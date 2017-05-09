package com.java.xdd.test;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Map;
import java.util.HashMap;

public class JsonTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test1(){
        Map<String, Object> params = new HashMap<>();
        params.put("a", "a");
        params.put("b", "b");
        params.put("c", "d");
        params.put("d", "");
        params.put("e", null);
        try {
            String s = mapper.writeValueAsString(params);
            System.out.println(s);
            s = JSONObject.toJSONString(params);
            System.out.println(s);

        } catch (Exception e) {

        }
    }
}
