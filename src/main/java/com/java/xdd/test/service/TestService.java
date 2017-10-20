package com.java.xdd.test.service;

import java.util.List;
import java.util.Map;

/**
 * Created by qw on 2017/10/19.
 */
public interface TestService {
    List<Map<String, Object>> test1();

    List<Map<String,Object>> testQuery(Map<String, Object> params);

    int testInsert(Map<String, Object> params);
}
