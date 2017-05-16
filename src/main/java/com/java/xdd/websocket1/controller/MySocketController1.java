package com.java.xdd.websocket1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.xdd.shiro.realm.CustomRealm;
import com.java.xdd.websocket1.handler.WebsocketEndPoint;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/websocket")
public class MySocketController1 {

    //使用http请求给websocket指定用户发送请求
    //@RequiresRoles("admin")
    @RequiresPermissions(value = "user:add")
    @RequestMapping("mySocketTest9")
    @ResponseBody
    public String aa(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Object thisKey = request.getSession().getAttribute("this_key");
        if (thisKey == null) {
            // session.setAttribute("this_key", "this_value");
        }
        System.out.println(thisKey);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id",1);
        resultMap.put("name","小明");
        resultMap.put("age",20);

        ObjectMapper mapper = new ObjectMapper();
        String msg = "";
        try {
            msg = mapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        TextMessage msgByUserId = new TextMessage(msg);
        //getWebsocketEndPoint().sendMessagesToUsers(msgByUserId);

        return msgByUserId.toString();
    }
    @RequestMapping("mySocketTest10")
    @ResponseBody
    public String bb(){
        return "error is 没有权限";
    }

    private WebsocketEndPoint websocketEndPoint;


    /**
     * 注入发送消息类实例
     * @return
     */
    public WebsocketEndPoint getWebsocketEndPoint() {
        return this.websocketEndPoint;
    }

    @RequestMapping("/websocket")
    public String websocket(){
        return "websocket";
    }

    @RequestMapping("/toWebsocket")
    public String toWebsocket(){
        return "websocket/websocket";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

}
