package com.java.xdd.blog.controller;

import com.java.xdd.blog.domain.Blog;
import com.java.xdd.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 博客controller
 */
@CrossOrigin
@Controller
@RequestMapping("/blog")
public class BlogContrller {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService; //博客

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> save(Blog blog){
        try {
            blogService.saveBlog(blog);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Blog> get(@RequestBody Blog blog){
        try {
            Blog queryBlog = blogService.getBlog(blog);
            return ResponseEntity.ok(queryBlog);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    @RequestMapping(value = "/get1",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> get1(@RequestBody Map<String, Object> blog){
        try {
            Map<String, Object> objectMap = blogService.get(blog);
            return ResponseEntity.ok(objectMap);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/get2",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> get2(@RequestBody Map<String, Object> blog){
        try {
            List<Map<String, Object>> blogService2 = blogService.get2(blog);
            return ResponseEntity.ok(blogService2);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**存储过程与存储函数*/
    @RequestMapping(value = "/get3", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> get3(@RequestBody Map<String, Object> blog){
        try {
            List<Map<String, Object>> blogService2 = blogService.get3(blog);
            return ResponseEntity.ok(blogService2);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(HttpServletResponse response, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        Cookie cookie = new Cookie("key", "value");
        cookie.setMaxAge(10000000);
        response.addCookie(cookie);
        return "abc";
    }





}
