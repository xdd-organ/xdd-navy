package com.java.xdd.rambda;

import com.java.xdd.shiro.domain.User;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.function.*;

/**
 * Created by qw on 2017/9/1.
 */
public class Demo {

    //rambda表达式
    @Test
    public void test() {
        //有参无返回值
        Consumer<String> con2 = new Consumer<String>() {
            @Override
            public void accept(String arg) {
                System.out.println(arg);
            }
        };
        Consumer<String> con = (arg) -> System.out.println(arg);
        con.accept("有参无返回值！");

        //有参有返回值
        Function<String, String> fun = (var1) -> var1;
        System.out.println(fun.apply("有参有返回值"));

        //有参返回布尔值
        Predicate<String> pre = (arg) -> StringUtils.isEmpty(arg);
        System.out.println(pre.test(null));

        //无参有返回值
        Supplier<String> sup = () -> "无参有返回值";
        System.out.println(sup.get());
    }

    //方法引用
    @Test
    public void test2() {
        //对象::实例方法
        Consumer<String> con = (arg) -> System.out.println(arg);
        Consumer<String> con2 = System.out::println;

        //类::静态方法
        IntBinaryOperator intb = (x, y) -> Math.addExact(x, y);
        IntBinaryOperator intb2 = Math::addExact;

        //类::实例方法
        //注意： 当需要引用方法的第一个参数是调用对象，并且第二个参数是需要引
        //用方法的第二个参数(或无参数)时： ClassName::methodName
        Comparator<Integer> com = (x, y) -> x.compareTo(y);
        Comparator<Integer> com2 = Integer::compareTo;

    }

    //构造器引用
    @Test
    public void test3() {
        //无参构造器引用
        Supplier<User> sup = () -> new User();
        Supplier<User> sup2 = User::new;
        System.out.println(sup2.get());

        //有参构造器引用
        Function<Long, User> fun = (l) -> new User(l);
        Function<Long, User> fun2 = User::new;
        System.out.println(fun2.apply(2L));

    }

    //数组引用
    @Test
    public void test4() {
        Function<Integer, Integer[]> fun = (x) -> new Integer[x];
        Function<Integer, Integer[]> fun2 = Integer[]::new;
        System.out.println(fun2.apply(2).length);
    }
}

