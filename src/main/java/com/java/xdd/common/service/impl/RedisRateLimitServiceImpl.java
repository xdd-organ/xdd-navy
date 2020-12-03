package com.java.xdd.common.service.impl;

import com.jiulingtao.common.service.common.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class RedisRateLimitServiceImpl implements RateLimitService {

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    @Autowired(required = false)
    @Qualifier("redisRateLimitLua")
    private RedisScript<List<Long>> redisLua;

    public static final String RATE_LIMIT_LUA_FILE_DIRECTORY = "lua/";

    @Bean("redisRateLimitLua")
    public DefaultRedisScript redisRateLimitLua() throws IOException {
        DefaultRedisScript luaScript = new DefaultRedisScript();
        luaScript.setScriptText(new ResourceScriptSource(new ClassPathResource(RATE_LIMIT_LUA_FILE_DIRECTORY + "rateLimit.lua")).getScriptAsString());
        luaScript.setResultType(List.class);
        return luaScript;
    }

    // routeId也就是我们的路由配置主键，id就是限流的URL
    @Override
    public boolean isAllowed(String routeId, String id) {
        List<Long> results = this.redisTemplate.execute(this.redisLua, this.getKeys(id), this.getBucketParams(routeId));
        return (!CollectionUtils.isEmpty(results) && results.get(0) == 1L);
    }

    private List<String> getKeys(String id) {
        String prefix = "request_rate_limiter.{" + id;
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    private String[] getBucketParams(String routeId) {
        // 允许用户每秒做多少次请求
        int replenishRate = 100;

        // 令牌桶的容量，允许在一秒钟内完成的最大请求数
        int burstCapacity = 100;
        List<String> scriptArgs = Arrays.asList(replenishRate + "",
                burstCapacity + "", Instant.now().getEpochSecond() + "", "1");
        return scriptArgs.toArray(new String[0]);
    }

}
