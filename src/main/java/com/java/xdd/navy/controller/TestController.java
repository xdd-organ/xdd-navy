package com.java.xdd.navy.controller;

import com.java.xdd.test.service.TestService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    @ResponseBody
    public Boolean test(@RequestParam("itemId") String itemId){
        System.out.println(itemId);
        return true;
    }

    @RequestMapping("/test2")
    @ResponseBody
    public List<String> test2(){
        ArrayList<String> list = new ArrayList<>();
        list.add("2017-08-21");
        list.add("2017-08-22");
        list.add("2017-08-23");
        return list;
    }

    @RequestMapping("/test3")
    @ResponseBody
    public String test3(@RequestParam(value = "echostr", required = false) String echostr
                              , @RequestParam(value = "signature", required = false) String signature
                              , @RequestParam(value = "timestamp", required = false) String timestamp
                              , @RequestParam(value = "nonce", required = false) String nonce
                              , HttpServletRequest request , HttpServletResponse response
                              , @RequestBody(required = false) String body) throws Exception{
        ServletOutputStream outputStream = response.getOutputStream();
        String appID = "wx6e79df62a7927bfd";
        String appsecret = "22166bbb0171cf0f5fa39fc5e5c466e9";
        String access_token = "xAFjD-g0B-YZwWZavyZk6sa4w97q3sqRNrK9JxLi8HkaeZI011f--vV1973AoywcPBiIdzCvTfMkTdTLMhUQfoqm1gpQb5yj2M9WrTd3_CJeKib5idgn-kt2GwML7XWIGJPjAAABEK";
        String token = "123456789";
        String EncodingAESKey = "cjHJTVlZ2Pj5rVgliOG5RD0wWRxfDKyfO4DkmhIVZ9v";


        Map parameterMap = request.getParameterMap();
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("请求的url--->" + requestURL.toString());
        System.out.println("body参数是--->" + body);

        String aa = token + timestamp + nonce;

        SAXReader saxReader = new SAXReader();
        String content = null;
        if (!StringUtils.isEmpty(body)) {
            try {
                InputStream is = new ByteArrayInputStream(body.getBytes());
                Document read = saxReader.read(is);
                Element root = read.getRootElement();
                String text = root.getText();
                String stringValue = root.getStringValue();
                String name = root.getName();

                List<Element> elements = root.elements();
                for (Element element : elements) {
                    if ("Content".equals(element.getName())) {
                        content = element.getStringValue();
                    }
                }

                for (Element element : elements) {
                    String text1 = element.getText();
                    System.out.println(element.getName() + "<--key:value-->" + element.getStringValue());
                    if ("MsgType".equals(element.getName()) && "text".equals(element.getStringValue())) {
                        String res = "<xml>\n" +
//                                "<ToUserName><![CDATA[oOpqqv-Pg6aHeE1ia4eweGFEB-Jo]]></ToUserName>\n" +
//                                "<FromUserName><![CDATA[gh_7a362dee10da]]></FromUserName>\n" +
                                "<ToUserName><![CDATA[oxFk3wdN0bzrl4m4uLo9bx5u5loY]]></ToUserName>\n" +
                                "<FromUserName><![CDATA[gh_d622de82f787]]></FromUserName>\n" +
                                "<CreateTime>1502695786</CreateTime>\n" +
                                "<MsgType><![CDATA[text]]></MsgType>\n" +
                                "<Content><![CDATA[" + content + "]]></Content>\n" +
                                "</xml>";
                        return res;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                String res = "<xml>\n" +
                        "<ToUserName><![CDATA[oxFk3wdN0bzrl4m4uLo9bx5u5loY]]></ToUserName>\n" +
                        "<FromUserName><![CDATA[gh_d622de82f787]]></FromUserName>\n" +
                        "<CreateTime>1502695786</CreateTime>\n" +
                        "<MsgType><![CDATA[text]]></MsgType>\n" +
                        "<Content><![CDATA[" + content + "]]></Content>\n" +
                        "</xml>";
                return res;
            }
        }


        String md5 = getMd5(aa, "sha-1");

        if (md5.equals(signature)) {
            return signature;
        } else {
            return echostr;
        }
    }

    public static String getMd5(String plainText, String abc) {
        try {
            MessageDigest md = MessageDigest.getInstance(abc);
            md.update("123456".getBytes());
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/aa")
    @ResponseBody
    public String aa() {
        testAsyncMethod();
        System.out.println("我已经执行了！");
        return "我已经执行了！";
    }

    @Async(value = "myexecutor")
    public void testAsyncMethod(){
        try {
            //让程序暂停100秒，相当于执行一个很耗时的任务
            Thread.sleep(3000);
            System.out.println("fadsf");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    private TestService testService;

    @RequestMapping("test1")
    public List<Map<String, Object>> test1() {
        List<Map<String, Object>> map = testService.test1();
        return map;
    }

    @RequestMapping("testQuery")
    public List<Map<String, Object>> testQuery(@RequestBody(required = false) Map<String, Object> params) {
        List<Map<String, Object>> map = testService.testQuery(params);
        return map;
    }

    @RequestMapping("testUpdate")
    public int testUpdate(@RequestBody(required = false) Map<String, Object> params) {

        return 1;
    }

    @RequestMapping("testInsert")
    public int testInsert(@RequestBody(required = false) Map<String, Object> params) {
        return testService.testInsert(params);
    }

    @RequestMapping("test4")
    public Object test4() {
        String abc = "a";
        logger.info(abc);
        logger.error(abc);
        logger.debug(abc);
        Optional<String> abc1 = Optional.ofNullable(abc);
        return abc1.get();
    }
}
