package com.java.xdd.lambda;

import com.java.xdd.shiro.domain.User;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by qw on 2017/9/1.
 */
public class LambdaList {

    private List<User> users = Arrays.asList(
            new User(10L),
            new User(10L),
            new User(20L),
            new User(30L),
            new User(40L));

    List<String> list = Arrays.asList("a", "b", "c", "d", "e");
    List<Integer> list2 = Arrays.asList(1,2,3,4,5,6,7,8,9);


    //stream创建
    //parallelStream    并行流
    //stream    串行流
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

    //查找与匹配
    //allMatch  所有元素匹配
    //anyMatch  至少有一个元素匹配
    //noneMatch  没有元素元素匹配
    //findFirst  返回第一个元素
    //findAny  返回流中任意元素
    //count  流中数量
    //max  流中最大值
    //min  流中最小值
    @Test
    public void test5() {
        boolean flag = users.stream().allMatch((user) -> user.getId().compareTo(10L) == 1);
        System.out.println(flag);

        flag = users.stream().anyMatch((user) -> user.getId().compareTo(10L) == 1);
        System.out.println(flag);

        flag = users.stream().noneMatch((user) -> user.getId().compareTo(60L) == 1);
        System.out.println(flag);

        User user = users.stream().findFirst().get();
        System.out.println(user);

        user = users.parallelStream().findAny().get();
        System.out.println(user);

        long count = users.stream().count();
        System.out.println(count);
    }

    //reduce 归约
    @Test
    public void test7() {
        //计算总和
        Integer reduce = list2.stream().reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        //计算id总和
        Long reduce2 = users.stream().map(User::getId).reduce(0L, Long::sum);
        System.out.println(reduce2);
    }

    //collect 收集
    //Collectors.toList()  //收集到list
    //Collectors.toSet()  //收集到set
    //Collectors.toCollection(HashSet::new)  //收集到指定集合中
    //Collectors.counting()  //总数
    //Collectors.averagingDouble()  //平均值
    //Collectors.summarizingDouble()  //总和
    //Collectors.minBy()  //最小值
    //Collectors.maxBy()  //最大值
    @Test
    public void test8() {
        users.stream().map(User::getId).collect(Collectors.toList()).forEach(System.out::println);
        Collectors.toCollection(HashSet::new);

    }

    //分组   可以多级分组
    //groupingBy 可以多级分组
    @Test
    public void test9() {
        Map<Long, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getId));
        collect.forEach((x,y) -> {
            System.out.println(x);
            y.stream().forEach(System.out::println);
        });
    }

    //分片  只分true与false
    @Test
    public void test10() {
        Map<Boolean, List<User>> collect = users.stream().collect(Collectors.partitioningBy((u) -> u.getId() > 20));
        collect.forEach((x,y) -> {
            System.out.println(x);
            y.stream().forEach(System.out::println);
        });
    }

    //连接
    @Test
    public void test11() {
        String collect = users.stream().map(User::getUsername).collect(Collectors.joining(",", "head,", ",foot"));
        System.out.println(collect);
    }

    //排序
    //sorted    默认排序
    //sorted(Comparator)    自定义排序
    @Test
    public void test6() {
        Stream<String> stream = list.stream();
        stream.sorted().forEach(System.out::println);

        stream = list.stream();
        stream.sorted((o1,o2) -> -o1.compareTo(o2)).forEach(System.out::println);
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


    @Test
    public void test12() {
        users.stream().filter((user) -> user.getId() == 10L).forEach(user -> user.setUsername("abc"));
        users.stream().forEach(System.out::println);
    }
}
