package com.java.xdd.websocket1.handler;

import com.alibaba.fastjson.JSONObject;
import com.java.xdd.common.util.DateUtil;
import com.java.xdd.common.util.PrincipalUtil;
import com.java.xdd.shiro.domain.User;
import com.java.xdd.websocket1.domain.Message;
import com.java.xdd.websocket1.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

//websocket的核心
public class WebsocketEndPoint extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(WebsocketEndPoint.class);
    @Autowired
    private MessageService messageService;//消息处理

    //改造 {"房间号":{"用户id":"session", "用户id":"session"}}
    private static final Map<String, Map<Long, WebSocketSession>> users = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String payload = message.getPayload(); //接收到的消息
        User user = this.getSessionUser(session);
        logger.debug("用户【{}】发送的消息【{}】!!", user, payload);

        System.out.println(payload);
        Message msg = JSONObject.parseObject(payload, Message.class);
        msg.setIsRead(false);
        msg.setFrom(user.getId());
        msg.setInsertTime(DateUtil.getCurrentDate());
        msg.setInsertAuthor(user.getId());
        msg.setUpdateTime(DateUtil.getCurrentDate());
        msg.setUpdateAuthor(user.getId());

        Long sendTo = msg.getSendTo();//发送给谁
        if (null != sendTo) {
            Map<Long, WebSocketSession> sessions = users.get("0");
            WebSocketSession sendSession = sessions.get(sendTo);
            if (null != sendSession && sendSession.isOpen()) { //用户在线
                TextMessage returnMessage = new TextMessage(JSONObject.toJSONString(msg));
                msg.setIsRead(true);
                sendSession.sendMessage(returnMessage);//发送消息
            }
        }
        try {
            messageService.insertSelective(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //StandardWebSocketClient client = new StandardWebSocketClient();
    }

    //用户进入系统监听
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<Long, WebSocketSession> sessions = users.get("0"); //非群聊房间
        if (null == sessions) {
            sessions = new HashMap<>();
            users.put("0", sessions);
        }
        User user = this.getSessionUser(session);
        sessions.put(user.getId(), session);


        logger.info("用户【{}】成功进入了系统。。。", this.getSessionUser(session));
        System.out.println("成功进入了系统。。。");
    }

    //后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("后台出错了！报错原因【{}】，来自用户【{}】", exception.getMessage(), this.getSessionUser(session));
        System.out.println("后台出错了！");
    }

    //用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        User user = this.getSessionUser(session);
        logger.info("用户【{}】退出系统！！！退出原因【{}】", user, closeStatus);
        if (session.isOpen()) {
            users.get("0").remove(user.getId());
        }
        System.out.println("安全退出了系统");

    }

    private User getSessionUser(WebSocketSession session){
        if (null == session) return null;
        Principal principal = session.getPrincipal();
        return PrincipalUtil.getUserPrincipal(principal);
    }
















    /**
     * 给所有的用户发送消息
     */
    /* public void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                //isOpen()在线就发送
                if (user.isOpen()) user.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //用户进入系统监听
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功进入了系统。。。");
        users.add(session);
    }

    //后台错误信息处理方法
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("后台出错了！");
    }

    //用户退出后的处理，不如退出之后，要将用户信息从websocket的session中remove掉，这样用户就处于离线状态了，也不会占用系统资源
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (session.isOpen()) users.remove(session);
        System.out.println("安全退出了系统");

    }*/


}