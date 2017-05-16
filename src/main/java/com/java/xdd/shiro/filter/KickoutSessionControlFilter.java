package com.java.xdd.shiro.filter;

import com.java.xdd.shiro.domain.User;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 控制一个用户能同时在几个地方登陆
 * 继承AccessControlFilter，
 *      最常用的，该filter中onPreHandle调用isAccessAllowed和onAccessDenied决定是否继续执行。
 *      一般继承该filter，isAccessAllowed决定是否继续执行。onAccessDenied做后续的操作，
 *      如重定向到另外一个地址、添加一些信息到request域等等。
 *
 * AccessControlFilter中的对onPreHandle方法做了进一步细化，
 *      isAccessAllowed方法和onAccessDenied方法达到控制效果。
 *      这两个方法都是抽象方法，由子类去实现。到这一层应该明白。
 *      isAccessAllowed和onAccessDenied方法会影响到onPreHandle方法，
 *      而onPreHandle方法会影响到preHandle方法，而preHandle方法会达到控制filter链是否执行下去的效果。
 *      所以如果正在执行的filter中isAccessAllowed和onAccessDenied都返回false，
 *      则整个filter控制链都将结束，不会到达目标方法（客户端请求的接口），
 *      而是直接跳转到某个页面（由filter定义的，将会在authc中看到）
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        Session session = subject.getSession();
        User user = (User) subject.getPrincipal();
        Serializable sessionId = session.getId();
        //TODO 同步控制
        Deque<Serializable> deque = cache.get(user.getUsername());
        if (deque == null) {
            deque = new LinkedList<>();
            cache.put(user.getUsername(), deque);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if (kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if (kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
                e.printStackTrace();
            }
            saveRequest(request);
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }

        return true;
    }
}