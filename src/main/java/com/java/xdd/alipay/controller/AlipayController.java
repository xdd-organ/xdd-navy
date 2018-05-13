/*
package com.java.xdd.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.java.xdd.alipay.constant.AlipayConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * Created by qw on 2017/10/30.
 *//*

@RequestMapping("alipay")
@Controller
public class AlipayController {

    //第一步 获取auth_code
    //浏览器打开
    //https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2017083108479152&scope=auth_user&redirect_uri=http%3A%2F%2F120.78.143.106%2F
    //授权回调地址 获取 auth_code

    //第二步 使用auth_code换取接口access_token及用户userId
    //scope=auth_user 则需要使用第三步获取用户的详细信息，scope=auth_base则可以直接获取用户的userId
    //支付宝获取用户信息()
    @RequestMapping("getUserInfo2")
    @ResponseBody
    public String getUserInfo2(HttpServletRequest request2,HttpServletResponse response) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstant.URL,
                AlipayConstant.APP_ID, AlipayConstant.APP_PRIVATE_KEY,
                AlipayConstant.FORMAT, AlipayConstant.CHARSET,
                AlipayConstant.ALIPAY_PUBLIC_KEY, AlipayConstant.SIGN_TYPE);

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(request2.getParameter("code"));//即第一步的auth_code
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            System.out.println(oauthTokenResponse.getAccessToken());
            String accessToken = oauthTokenResponse.getAccessToken();
            return accessToken;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //第三步 调用接口获取用户信息 (scope=auth_user)
    //使用access_token获取用户信息
    @RequestMapping("getUserInfo3")
    @ResponseBody
    public String getUserInfo3(HttpServletRequest request2,HttpServletResponse response) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstant.URL,
                AlipayConstant.APP_ID, AlipayConstant.APP_PRIVATE_KEY,
                AlipayConstant.FORMAT, AlipayConstant.CHARSET,
                AlipayConstant.ALIPAY_PUBLIC_KEY, AlipayConstant.SIGN_TYPE);

        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        String auth_token = request2.getParameter("auth_token");//即第二步的access_token
        try {
            AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(request, auth_token);
            System.out.println(userinfoShareResponse.getBody());
            return userinfoShareResponse.getBody();
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
*/
