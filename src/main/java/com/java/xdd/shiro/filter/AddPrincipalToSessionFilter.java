package com.java.xdd.shiro.filter;

//为了使用RemenberMe Cookie，RememberMe Cookie里面是加密的Principle，
// 所以需要从这个Principle找出对应的user_id和phonenum放到Session里面

import com.java.xdd.common.util.CookieUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用记住我，第二次登录时恢复session数据
 * 继承OncePerRequestFilter
 *          只需实现doFilterInternal方法即可，在这里面实现filter的功能。
 *          切记在该方法中最后调用filterChain.doFilter(request, response)，
 *          允许filter链继续执行下去。可以在这个自定义filter中覆盖isEnable达到控制该filter是否需要被执行
 *          （实质是doFilterInternal方法）以达到动态控制的效果，一般不建议直接继承这个类;
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {

    @Autowired
    private CookieRememberMeManager rememberMeManager;

    /**  */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest request = WebUtils.getHttpRequest(subject);
        HttpServletResponse response = WebUtils.getHttpResponse(subject);
        String rememberMe = CookieUtils.getCookieValue(request, "rememberMe");
        rememberMeManager.forgetIdentity(null);
        // subject.logout(); // 退出登录
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setValue(rememberMe);
        String s = cookie.readValue(request, response);

        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        boolean authenticated = subject.isAuthenticated();
        boolean remembered = subject.isRemembered();
        Object principal = subject.getPrincipal();
        if (subject.isRemembered()) {
            Session session = subject.getSession();
            session.setAttribute("this_key", "this_value"); // 将数据存储到session中
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
