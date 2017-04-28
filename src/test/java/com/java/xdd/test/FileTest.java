package com.java.xdd.test;

import com.aliyun.oss.common.utils.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTest {

    @Test
    public void fileEncode() throws IOException{
        String path = System.getProperty("user.dir");

        String path1 = path + "\\src\\main\\resources\\config.properties";
        String path2 = path + "\\src\\main\\resources\\jdbc.properties";

        List<String> aa = new ArrayList<>();
        aa.add(path1);
        aa.add(path2);

        for (int j = 0; j < aa.size(); j++) {
            byte[] bytes = IOUtils.readStreamAsByteArray(new FileInputStream(aa.get(j)));
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] ^ 20000);
            }
            OutputStream out = new FileOutputStream(path + "\\src\\main\\resources\\" + j);
            out.write(bytes);
            out.close();
        }
    }

    @Test
    public void fileDecode() throws IOException{
        String path = System.getProperty("user.dir");

        String path1 = path + "\\src\\main\\resources\\0";
        String path2 = path + "\\src\\main\\resources\\1";

        List<String> aa = new ArrayList<>();
        aa.add(path1);
        aa.add(path2);

        for (int j = 0; j < aa.size(); j++) {
            byte[] bytes = IOUtils.readStreamAsByteArray(new FileInputStream(aa.get(j)));
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] ^ 20000);
            }
            OutputStream out = new FileOutputStream(path + "\\src\\main\\resources\\" + j + ".properties");
            out.write(bytes);
            out.close();
        }
    }



    @Test
    public void test1() throws IOException{
        int a = 5;
        int aa = a >> 1;
        System.out.println(aa);
        int bb = aa << 1;
        System.out.println(bb);

    }

    @Test
    public void test2() {
        String password = "123456";// 获取用户输入
        char[] array = password.toCharArray();// 获取字符数组
        for (int i = 0; i < array.length; i++) {// 遍历字符数组
            array[i] = (char) (array[i] ^ 20000);// 对每个数组元素进行异或运算
        }
        System.out.println("加密或解密结果如下：");
        System.err.println(new String(array));// 输出密钥

        for (int i = 0; i < array.length; i++) {// 遍历字符数组
            array[i] = (char) (array[i] ^ 20000);// 对每个数组元素进行异或运算
        }
        System.err.println("-----" + new String(array));// 输出密钥
    }

    @Test
    public void test3() {
        String password = "123456";// 获取用户输入
        byte[] array = password.getBytes();// 获取字符数组
        for (int i = 0; i < array.length; i++) {// 遍历字符数组
            array[i] = (byte) (array[i] ^ 20000);// 对每个数组元素进行异或运算
        }
        System.out.println("加密或解密结果如下：");
        System.err.println(new String(array));// 输出密钥

        for (int i = 0; i < array.length; i++) {// 遍历字符数组
            array[i] = (byte) (array[i] ^ 20000);// 对每个数组元素进行异或运算
        }
        System.err.println("-----" + new String(array));// 输出密钥
    }
}
