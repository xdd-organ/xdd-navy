package com.java.xdd.shiro.controller;

import com.java.xdd.shiro.domain.User;
import com.java.xdd.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    @ResponseBody
    public List<User> findByUser(@RequestBody(required = false) User user){
        return userService.findByUser(user);
    }
}
