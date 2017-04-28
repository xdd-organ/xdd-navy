package com.java.xdd.solr.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericRangeQueryNode;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class Search {

    public void show(Query query) throws IOException, ParseException {

        // 定义搜索器
        Directory directory = FSDirectory.open(new File("index"));
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("命中的记录数：" + topDocs.totalHits);

        // 遍历结果
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println("得分：" + scoreDoc.score);
            // 查询属性内容
            Document document = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id:" + document.get("id"));
            System.out.println("title:" + document.get("title"));
            System.out.println("sellPoint:" + document.get("sellPoint"));
            System.out.println("image:" + document.get("image"));
        }

        // 关闭
        indexReader.close();
    }
    
    /**
     * TermQuery 词条搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testTermQuery() throws IOException, ParseException{
        Term term = new Term("title", "手机");
        Query query = new TermQuery(term);
        show(query);
    }
    
    /**
     * NumericRangeQuery 范围搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testNumericRangeQuery() throws IOException, ParseException{
        //设置查询字段、最小值、最大值、最小值是否包含边界，最大值是否包含边界
        Query query = NumericRangeQuery.newLongRange("id", 536563L, 536663L, true, true);
        show(query);
    }
    
     

    /**
     * MatchAllDocsQuery 匹配所有搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testMatchAllDocsQuery() throws IOException, ParseException{
        Query query = new MatchAllDocsQuery();
        show(query);
    }
    
    /**
     * WildcardQuery 模糊搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testWildcardQuery() throws IOException, ParseException{
        Term term = new Term("title", "平*视");
        Query query = new WildcardQuery(term);
        show(query);
    }
    
    /**
     * FuzzyQuery 相似度搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testFuzzyQuery() throws IOException, ParseException{
        Term term = new Term("title", "wpplw");
        Query query = new FuzzyQuery(term, 2);
        show(query);
    }
    
    
    
    /**
     * BooleanQuery 布尔搜索
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testBooleanQuery() throws IOException, ParseException{
        BooleanQuery booleanQuery = new BooleanQuery();
        //必须包含
        booleanQuery.add(new TermQuery(new Term("title", "手机")), Occur.MUST);
        //不包含
        booleanQuery.add(new TermQuery(new Term("title", "apple")), Occur.MUST_NOT);
        //或 
        booleanQuery.add(new TermQuery(new Term("title", "诺基亚")), Occur.SHOULD);
        
        show(booleanQuery);
    }
}
