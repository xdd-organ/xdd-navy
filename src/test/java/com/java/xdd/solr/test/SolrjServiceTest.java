package com.java.xdd.solr.test;

import com.java.xdd.solr.domain.Foo;
import com.java.xdd.solr.service.SolrjService;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SolrjServiceTest {

    private SolrjService solrjService;
    
    private HttpSolrServer httpSolrServer;

    @Before
    public void setUp() throws Exception {
        String url = "http://127.0.0.1:8983/solr/";
        HttpSolrServer httpSolrServer = new HttpSolrServer(url); //定义solr的server
        httpSolrServer.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrServer.setMaxRetries(1); // 设置重试次数，推荐设置为1
        httpSolrServer.setConnectionTimeout(500); // 建立连接的最长时间

        this.httpSolrServer = httpSolrServer;
        solrjService = new SolrjService(httpSolrServer);
    }

    @Test
    public void testAdd() throws Exception {
        Foo foo = new Foo();
        foo.setId(System.currentTimeMillis() + "");
        foo.setTitle("你猜这是什么");

        this.solrjService.add(foo);
    }

    @Test
    public void testDelete() throws Exception {
        this.solrjService.delete(Arrays.asList("1416537175446"));
    }

    @Test
    public void testSearch() throws Exception {
        List<Foo> foos = this.solrjService.search("这", 1, 10);
        for (Foo foo : foos) {
            System.out.println(foo);
        }
    }
    
    @Test
    public void testDeleteByQuery() throws Exception{
        httpSolrServer.deleteByQuery("*:*");
        httpSolrServer.commit();
    }

}
