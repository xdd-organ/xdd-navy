package com.java.xdd.junit;

import org.junit.Test;

import java.io.Console;
import java.util.NavigableSet;
import java.util.Optional;

/**
 * Created by qw on 2017/9/7.
 */
public class Demo {
    @Test
    public void test() {
        Console cons = System.console();
        if (cons != null) {
            String str1 = cons.readLine();
            cons.format("%s\n", str1);

            char[] passwd = cons.readPassword();
            cons.format("%s\n",new String(passwd));
        }
    }

    /*public static void main(String[] args) {
        //因为Scanner不适合从控制台输入密码，所以引入Console类（在控制台输入时，这一行汉字要去掉，不然会报错）
        Console cons = System.console();
        if (cons != null) {
            String str1 = cons.readLine();
            cons.format("%s\n", str1);

            char[] passwd = cons.readPassword();
            cons.format("%s\n",new String(passwd));
        }

        Optional<String[]> args1 = Optional.ofNullable(args);
    }
*/
    @Test
    public void test2() {
        String abc = null;
        Optional<String> abc1 = Optional.ofNullable(abc);
        String abc2 = Optional.ofNullable(abc).orElse(null);
        //System.out.println(abc1.get());
        System.out.println(abc2);
    }
}
