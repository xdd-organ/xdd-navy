package com.java.xdd.test;

import com.java.xdd.common.httpclient.HttpClientUtil;
import com.java.xdd.common.httpclient.HttpResult;
import com.java.xdd.common.service.RedisService;
import com.java.xdd.common.service.impl.RedisServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisTest {
    private ShardedJedis shardedJedis;
    private HttpClientUtil httpClient;
    private RedisService redisService;

    @Before
    public void before(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        context.start();
        ShardedJedisPool shardedJedisPool = (ShardedJedisPool)context.getBean("shardedJedisPool");
        //httpClient = (HttpClientUtil) context.getBean("httpClientUtil");
        //redisService = context.getBean(RedisService.class);
        shardedJedis = shardedJedisPool.getResource();
    }

    @Test
    public void test(){
        try {
            HttpResult httpResult = httpClient.doPost("http://192.168.1.49:4080/order/queryManufactureOrderNum", null, null);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1(){
        String key = "pramas_1";
        String value = shardedJedis.get(key);
        System.out.println(value);
    }

    @Test
    public void test2(){
        String key = "myset";
        Set<String> values = shardedJedis.smembers(key);
        for (String str : values){
            System.out.println(str);
        }
    }
    @Test
    public void test3(){
        String key = "list";
        String[] strs = {"abc1","abc2","abc3"};
        Long lpush = shardedJedis.lpush(key, strs);
        System.out.println(lpush);
    }
    @Test
    public void test4(){
        List<String> list = shardedJedis.lrange("list", 0, 1);
        System.out.println(list);
    }
    @Test
    public void test5(){
        String key = "list2";
        String[] strs = {"abc1","abc2","abc3"};
        //Long lpush = redisService.lpush(key, strs);
        //System.out.println(lpush);
    }
    
    @Test
    public void test6(){
        String key = "map";
        Map<String, String> map = new HashMap<>();
        map.put("aa","a-1");
        map.put("bb","b-2");
        map.put("cc","c-3");
        map.put("dd","d-4");
        String hmset = shardedJedis.hmset(key, map);
        System.out.println(hmset);
        List<String> hmget = shardedJedis.hmget(key,"aa");
        System.out.println(hmget);
        shardedJedis.hvals(key);

        shardedJedis.hset(key,"ee","e-5");

    }

    //测试使用redis生产主键id
    @Test
    public void test7(){
        Long aa = shardedJedis.incr("aa");
        Runtime runtime = Runtime.getRuntime();
        System.out.println(aa);
    }







}
