package com.java.xdd.test;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.java.xdd.common.httpclient.HttpClientUtil;
import com.java.xdd.common.httpclient.HttpUtils;
import com.java.xdd.common.util.IPLocationUtil;
import com.java.xdd.system.domain.SystemLog;
import com.java.xdd.system.service.SystemLogService;
import com.java.xdd.system.service.impl.SystemLogServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.ShardedJedisPool;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by huanghu on 2017/4/8.
 */
public class HttpTest {
    private HttpClientUtil httpClient;

    //@Before
    public void before(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        context.start();
        ShardedJedisPool shardedJedisPool = (ShardedJedisPool)context.getBean("shardedJedisPool");
        httpClient = (HttpClientUtil) context.getBean("httpClientUtil");
    }

    @Test
    public void test(){
        try {
            String host = "https://dm-81.data.aliyun.com";
            String path = "/rest/160601/ip/getIpInfo.json";
            String method = "";
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE 1615401819d3477cbdaa21107e749111");
            Map<String, String> querys = new HashMap<>();
            querys.put("ip", "110.53.148.178");

            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);

            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2(){
        try {
            String host = "https://api.map.baidu.com";
            String path = "/location/ip";
            String method = "";
            Map<String, String> headers = new HashMap<>();
            //headers.put("Authorization", "APPCODE 1615401819d3477cbdaa21107e749111");
            Map<String, String> querys = new HashMap<>();
            querys.put("ip", "116.25.45.16");
            querys.put("ak", "zDyZneP3vBERNestqdHYzsWQ60KfLDy0");

            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);

            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test3(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        context.start();
        IPLocationUtil locationUtil = context.getBean(IPLocationUtil.class);
        //String ipLocationUtil = locationUtil.getIPLocation(null);
        //System.out.println(ipLocationUtil);

        SystemLogService logService = context.getBean(SystemLogService.class);
        /*SystemLog bySystemLogId = logService.findBySystemLogId(832L);
        String ip = bySystemLogId.getIp();
        String ipLocation = locationUtil.getIPLocation(ip);
        bySystemLogId.setIpLocation(ipLocation);
        logService.update(bySystemLogId);*/

        List<SystemLog> systemLogs = logService.listSystemLog();
        for (SystemLog systemLog : systemLogs) {
            systemLog.setIpLocation(locationUtil.getIPLocation(systemLog.getIp()));
            logService.update(systemLog);
        }


    }

    @Test
    public void test4(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-aliyunoss.xml");
        context.start();
        OSSClient ossClient = context.getBean(OSSClient.class);

        String bucketName = "xdd-navy-oss";
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, "123");
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        String uploadId = result.getUploadId();
        System.out.println(uploadId);
        //C36B89D0B79E40808277B7C866C1A175
        //75D6509522414A4A8AC80AF6CD467D97
        //D916F01A3F684C418C8376E2D43CA670


    }

    @Test
    public void test5() throws Exception{
        File file = new File("F:\\websocket.css");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.readStreamAsByteArray(new FileInputStream(file));
        String md5 = BinaryUtil.encodeMD5(bytes);
        System.out.println(md5);
        //AA6983072592EF08100306E17BF322A5
        //aa6983072592ef08100306e17bf322a5
    }

    @Test
    public void test6() {
        String path = "G:\\tmp\\file";
        File file = new File(path + File.separator + "aa.txt");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private List<String> sort(List<String> chunkList){
        Collections.sort(chunkList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Long a = Long.valueOf(o1.substring(0, o1.lastIndexOf(".")));
                Long b = Long.valueOf(o2.substring(0, o2.lastIndexOf(".")));
                return a.compareTo(b); //升序
            }
        });
        return chunkList;
    }

    @Test
    public void test7() {
        List<String> chunkList = new ArrayList<>();

        chunkList.add("1.a");
        chunkList.add("0.a");
        chunkList.add("11.a");
        chunkList.add("8.a");
        chunkList.add("7.a");
        chunkList.add("2.a");
        chunkList.add("5.a");


        List<String> integerList = this.sort(chunkList);
        System.out.println(chunkList);
        System.out.println(integerList);
    }


}
