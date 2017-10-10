package com.java.xdd.mybatis.controller;

import com.java.xdd.mybatis.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by qw on 2017/10/10.
 */
@RestController
@RequestMapping("mybatis")
public class MybatisController {

    @Autowired
    private MybatisService mybatisService;

    @PostMapping(value = "getMybatis")
    public Map<String, Object> getMybatis(@RequestParam(value = "id", required = false) Long id) {
        Map<String, Object> mybatis = mybatisService.getMybatis(id == null ? 1 : id);
        return mybatis;
    }
}
