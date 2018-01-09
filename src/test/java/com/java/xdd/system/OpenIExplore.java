package com.java.xdd.system;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;
  
public class OpenIExplore{

    //打开网页
    public static void main(String[] args) throws IOException{
        Properties properties = System.getProperties();
        String osName = properties.getProperty("os.name");
          
        System.out.println (osName);
          
        if (osName.indexOf("Linux") != -1) {
            Runtime.getRuntime().exec("htmlview");
        } else if (osName.indexOf("Windows") != -1){
            Runtime.getRuntime().exec("explorer http://www.baidu.com");
        } else {
            throw new RuntimeException("Unknown OS.");
        }
    }

    //打开程序
    @Test
    public void test()  throws IOException{
        Runtime.getRuntime().exec("D:\\Program Files (x86)\\亿图图示 9.0\\EdrawMax.exe");
    }

    //关闭程序
    @Test
    public void test2()  throws IOException{
        Runtime.getRuntime().exec("taskkill /F /IM EdrawMax.exe");
    }











}