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
    @Test
    public void test2(){
        int[] segment = new int[1024];

        int[] count = {2,1};
        int[] length = {256,512};

        int segmentLength = 0;
        for (int i = 0; i < count.length; i++) {
            segmentLength += count[i];
        }
        int[] ai = new int[segmentLength + 1];

        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                ai[++index] = ai[index - 1] + length[i];
            }
        }
        if (ai[ai.length - 1] != 1024) {
            throw new RuntimeException("error,check your partitionScope definition.");
        }

        // 数据映射操作
        for (int i = 1; i < ai.length; i++) {
            for (int j = ai[i - 1]; j < ai[i]; j++) {
                segment[j] = (i - 1);
            }
        }

        int i = 1025 & 1023;
        System.out.println(i);
        System.out.println(segment[i]);
    }
}
