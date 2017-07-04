package com.java.xdd.proxy.cglibproxy;


public class MainTest {
    public static void main(String args[]) throws InterruptedException {
        //创建我们的代理类
        ProxyFactory cgLibProxy = new ProxyFactory();

        //通过enhancer创建我们的子类
        //因为oldClass是我们子类的父类，所以这里向上转型成功
        OldClass oldClass = (OldClass) cgLibProxy.createSubObject(OldClass.class);

        oldClass.method1();//调用方法
        oldClass.method2();//调用方法
        oldClass.method3("zenghao");//调用方法
    }
}
