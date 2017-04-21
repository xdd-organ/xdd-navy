package com.java.xdd.shiro.domain;

public enum RoleEnum {

    ADMIN("管理员");

    private String name;

    RoleEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
