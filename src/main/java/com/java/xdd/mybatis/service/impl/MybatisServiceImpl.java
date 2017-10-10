package com.java.xdd.mybatis.service.impl;

import com.java.xdd.mybatis.mapper.MybatisMapper;
import com.java.xdd.mybatis.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by qw on 2017/10/10.
 */
@Service(value = "mybatisService")
public class MybatisServiceImpl implements MybatisService{

    @Autowired
    private MybatisMapper mybatisMapper;

    @Override
    public Map<String, Object> getMybatis(Long id) {
        Map<String, Object> res = mybatisMapper.getMybatis(id);
        return res;
    }
}
