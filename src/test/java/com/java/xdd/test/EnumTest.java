package com.java.xdd.test;

import com.java.xdd.shiro.domain.RoleEnum;
import org.junit.Test;

public class EnumTest {
    @Test
    public void test1(){
        RoleEnum admin = RoleEnum.ADMIN;
        String name = admin.name();
        System.out.println(name);
        System.out.println(RoleEnum.ADMIN.getName());
        System.out.println(RoleEnum.ADMIN.name());
    }
}
