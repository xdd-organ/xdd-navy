package com.java.xdd.common.service;

public interface RateLimitService {

    /**
     * 是否可以访问
     * @param routeId 配置信息
     * @param id 限流关键信息
     */
    boolean isAllowed(String routeId, String id);
}
