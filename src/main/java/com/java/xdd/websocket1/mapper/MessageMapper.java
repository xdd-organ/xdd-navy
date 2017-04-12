package com.java.xdd.websocket1.mapper;


import com.java.xdd.websocket1.domain.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int insert(@Param("pojo") Message pojo);

    int insertSelective(@Param("pojo") Message pojo);

    int insertList(@Param("pojos") List<Message> pojo);

    int update(@Param("pojo") Message pojo);
}
