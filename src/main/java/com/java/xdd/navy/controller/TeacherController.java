package com.java.xdd.navy.controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("teacher")
public class TeacherController {
    private static final List<Map<String, Object>> testList = new ArrayList<>();

    @RequestMapping("addTeacher")
    public Map<String, Object> addTeacher(@RequestBody Map<String, Object> params) {
        testList.add(params);
        return params;
    }

    @RequestMapping("removeTeacher")
    public List<Map<String, Object>> removeTeacher(@RequestParam(value = "id") int id) {
        if (!CollectionUtils.isEmpty(testList)) {
            for (Map<String, Object> map : testList) {
                if (Integer.valueOf(map.get("id").toString()) == id) {
                    testList.remove(map);
                    break;
                }
            }
        }

        return testList;
    }

    @RequestMapping("findTeacher/{id}")
    public Map<String, Object> findTeacher(@PathVariable("id") int id) {
        Map<String, Object> res = null;
        if (!CollectionUtils.isEmpty(testList)) {
            for (Map<String, Object> map : testList) {
                if (Integer.valueOf(map.get("id").toString()) == id) {
                    res = map;
                    break;
                }
            }
        }

        int a,b,c = 1;

        return res;
    }
}
