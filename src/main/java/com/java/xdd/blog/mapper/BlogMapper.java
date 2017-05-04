package com.java.xdd.blog.mapper;

import com.github.abel533.mapper.Mapper;
import com.java.xdd.blog.domain.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper extends Mapper<Map<String, Object>>{

    Long saveBlog(Blog blog);

    Blog getBlog(Blog blog);

    String get();

    List<Map<String, Object>> get3(Map<String, Object> blog);

    List<Map<String, Object>> get4(Map<String, Object> blog);
}
