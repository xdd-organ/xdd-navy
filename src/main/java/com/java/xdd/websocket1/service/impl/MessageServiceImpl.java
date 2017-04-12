package com.java.xdd.websocket1.service.impl;

import com.java.xdd.common.service.impl.BaseServiceImpl;
import com.java.xdd.websocket1.domain.Message;
import com.java.xdd.websocket1.mapper.MessageMapper;
import com.java.xdd.websocket1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int insert(Message pojo){
        return messageMapper.insert(pojo);
    }

    @Override
    public int insertSelective(Message pojo){
        return messageMapper.insertSelective(pojo);
    }

    @Override
    public int insertList(List<Message> pojos){
        return messageMapper.insertList(pojos);
    }

    @Override
    public int update(Message pojo){
        return messageMapper.update(pojo);
    }
}
