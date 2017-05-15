package com.java.xdd.shiro.session;

import com.java.xdd.common.service.RedisService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的sessionDAO管理策略[默认存储在内存中：SessionDAO sessionDAO = new MemorySessionDAO()]
 * 我们让session存储在redis中，可以做分布式集群session共享
 */
public class CustomRedisSessionDAO extends AbstractSessionDAO{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // sessionId生成策略，默认使用UUID
    private SessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();

    private RedisService redisService;

    /**
     * 设置sessionId生成策略
     * @param sessionIdGenerator
     */
    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    /**
     * 创建一个session，并使用sessionId生成策略
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);

        // 存储到redis中
        //this.saveSession(session);
        return sessionId;
    }

    /**
     * 根据sessionId读取session
     * @param sessionId 该参数是sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("session id is null");
            return null;
        }

        //从redis中读取session的字节文件
        String s1 = redisService.get(sessionId.toString());
        //session字节文件反序列化成Session对象
        // Session s = (Session) SerializeUtils.deserialize();
        return null;
    }

    /**
     * 更新session数据
     * @param session
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {

    }

    /**
     * 删除session
     * @param session
     */
    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }

        // 删除redis的session
        redisService.del(session.getId().toString());
    }

    /**
     * 获取当前活动的session
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();

        // 获取所有的session
        //Set<String> hkeys = redisService.hkeys("");

        return null;
    }
}
