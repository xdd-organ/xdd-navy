package com.java.xdd.shiro.filter;

import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 集成AdviceFilter
 *          中提供三个方法preHandle postHandle afterCompletion：
 *          若需要在目标方法执行前后都做一些判断的话应该继承这个类覆盖preHandle 和postHandle 。
 */
public class CustomAdviceFilter extends AdviceFilter{

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        super.postHandle(request, response);
    }
}
