package com.java.xdd.test;

import com.java.xdd.common.httpclient.HttpUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SmsTest {
    @Test
    public void test() {
        String host = "";
        String path = "";
        String method = "";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        try {
            //HttpUtils.doPost(host, path, method, headers, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try{
            String postData="account=yishuju&password=153852&mobile=17666127938&content="+ URLEncoder.encode("您的验证码是123456，请在网页中输入完成验证，如非本人操作，请忽略此短信【医数聚】","utf-8");
            String postUrl="http://sms.chanzor.com:8001/sms.aspx?action=send";
            //String postUrl="http://api.chanzor.com/send";
            SmsTest.sms(postData,postUrl);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public static void sms(String postData, String postUrl) {
        String res = null;
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                res = "";
                System.out.println(res);
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            res = result;
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        System.out.println(res);
    }
}
