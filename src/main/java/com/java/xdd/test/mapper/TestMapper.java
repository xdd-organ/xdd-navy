package com.java.xdd.test.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by qw on 2017/10/19.
 */
public interface TestMapper {
    List<Map<String, Object>> test1();

    List<Map<String,Object>> testQuery(Map<String, Object> params);

    int testInsert(Map<String, Object> params);
}
