package com.java.xdd.test.service.impl;

import com.java.xdd.test.mapper.TestMapper;
import com.java.xdd.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by qw on 2017/10/19.
 */
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Map<String, Object>> test1() {
        return testMapper.test1();
    }

    @Override
    public List<Map<String, Object>> testQuery(Map<String, Object> params) {
        return testMapper.testQuery(params);
    }

    @Override
    public int testInsert(Map<String, Object> params) {
        return testMapper.testInsert(params);
    }
}
