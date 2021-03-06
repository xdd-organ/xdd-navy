package com.java.xdd.blog.service.impl;


import com.java.xdd.blog.domain.Blog;
import com.java.xdd.blog.mapper.BlogMapper;
import com.java.xdd.blog.service.BlogService;
import com.java.xdd.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("blogService")
public class BlogServiceImpl extends BaseServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper; //

    @Override
    public Long saveBlog(Blog blog) {
        Long blogId = blogMapper.saveBlog(blog);
        return blogId;
    }

    @Override
    public Blog getBlog(Blog blog) {
        return blogMapper.getBlog(blog);
    }

    @Override
    public Map<String, Object> get(Map<String, Object> params) {
        return blogMapper.selectOne(params);
    }

    @Override
    public List<Map<String, Object>> get2(Map<String, Object> params) {
        return blogMapper.select(params);
    }

    /**存储过程与存储函数*/
    @Override
    public List<Map<String, Object>> get3(Map<String, Object> blog) {
        String s = blogMapper.get();
        System.out.println(s);
        //List<Map<String, Object>> aa = blogMapper.get3(blog);
        //System.out.println(aa);
        List<Map<String, Object>> bb = blogMapper.get4(blog);
        System.out.println(bb);
        return bb;
    }
}
