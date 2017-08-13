package com.java.xdd.navy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public Boolean test(@RequestParam("itemId") String itemId){
        System.out.println(itemId);
        return true;
    }

    @RequestMapping("/test2")
    @ResponseBody
    public List<String> test2(){
        ArrayList<String> list = new ArrayList<>();
        list.add("2017-08-21");
        list.add("2017-08-22");
        list.add("2017-08-23");
        return list;
    }
}
