package com.java.xdd.rambda;

import com.java.xdd.shiro.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by qw on 2017/9/1.
 */
public class Demo2 {

    private List<User> users = Arrays.asList(new User(10L),
            new User(20L),
            new User(30L),
            new User(40L));


    //stream创建
    @Test
    public void test() {
        //通过集合创建流
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();

        //通过Arrays中的静态方法stream
        String[] strArr = new String[10];
        Stream<String> stream1 = Arrays.stream(strArr);

        //通过stream中的静态方法of
        Stream<String> a = Stream.of("a", "b", "c");

        //无限流
        Stream<Integer> iterate = Stream.iterate(0, (i) -> i + 2);
        iterate.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random());
    }

    //stream中间操作
    //filter 过滤
    //limit 取前n个
    //skip 跳过前n个
    //distinct 去重(通过hashCode与equals去重)
    @Test
    public void test2() {
        Stream<User> stream = users.stream();
        stream.filter((u) -> u.getId() > 20L)//过滤操作
              .forEach(System.out::println);//遍历操作

    }

    //映射
    @Test
    public void test4() {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e");
        list.stream().map((str) -> str.toUpperCase()).forEach(System.out::println);

    }

    //stream终止操作
    @Test
    public void test3() {
        users.forEach(System.out::println);
    }


}
