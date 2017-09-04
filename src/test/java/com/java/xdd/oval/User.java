package com.java.xdd.oval;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

/**
 * Created by qw on 2017/9/4.
 */
public class User {
    @NotNull(message = "主键id不能为空！")
    private Long id;
    @Length(max = 10, min = 2, message = "名称长度2-10！")
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
