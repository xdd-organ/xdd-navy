package com.java.xdd.lambda;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Created by Administrator on 2017/9/2.
 */
public class Demo {
    @Test
    public void test() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTask.adapt(() -> 1);
    }

}
