package com.java.xdd.websocket1.service;

import com.java.xdd.common.service.BaseService;
import com.java.xdd.websocket1.domain.Message;

import java.util.List;

public interface MessageService extends BaseService {
    int insert(Message pojo);

    int insertSelective(Message pojo);

    int insertList(List<Message> pojos);

    int update(Message pojo);
}
