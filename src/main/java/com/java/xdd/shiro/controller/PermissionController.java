package com.java.xdd.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.java.xdd.shiro.domain.Permission;
import com.java.xdd.shiro.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/savePermission", method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles(value = {"ADMIN"})
    public String savePermission(@Valid@RequestBody Permission permission, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> res = new HashMap<>();
            for (FieldError allError : result.getFieldErrors()) {
                res.put(allError.getField(), allError.getDefaultMessage());
            }
            return JSONObject.toJSONString(res);
        }
        return "操作成功！";
    }



}
