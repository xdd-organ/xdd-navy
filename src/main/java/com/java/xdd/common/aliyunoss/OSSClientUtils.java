package com.java.xdd.common.aliyunoss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对OSSClient各个方法注释
 */
public class OSSClientUtils {
    private final static String bucketName = "xdd-navy-oss";
    private static String firstKey = "name.suffix";

    @Autowired
    private OSSClient ossClient;

    //普通上传

    /**
     * 上传本地文件到oss
     * @param uploadFileRequest 本地文件对象(构造函数参数：bucketName ：顾名思义;key ：文件保存在oss的名称)
     *   partSize 分片大小，单位字节，默认100KB
     *   taskNum 分片上传线程数，默认1
     *   uploadFile 需要上传的本地文件路径
     *   enableCheckpoint 是否开启断点续传
     *   checkpointFile 断点续传时保存分片上传的信息的本地文件
     *   objectMetadata 上传对象的元数据
     *   callback 回调
     *
     */
    public CompleteMultipartUploadResult uploadFile(UploadFileRequest uploadFileRequest){
        try {
            UploadFileResult uploadFileResult = ossClient.uploadFile(uploadFileRequest);
            return uploadFileResult.getMultipartUploadResult();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * 上传流文件到oss
     * @param uploadPartRequest(支持分块上传)
     *   uploadId 标识Multipart上传事件的Upload ID
     *   partNumber 上传分块（Part）的标识号码（Part Number）
     *   partSize = -1 返回分块（Part）数据的字节数
     *   md5Digest 分块（Part）数据的MD5校验值
     *   inputStream 上传内容的数据流
     *   useChunkEncoding = false 是否采用Chunked编码方式传输请求数据
     * @return
     */
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest){
        UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
        return uploadPartResult;
    }

    public CompleteMultipartUploadResult multipartUpload(UploadPartRequest uploadPartRequest, String key){
        String uploadId = claimUploadId(key);
        List<PartETag> partETags = null;
        CompleteMultipartUploadRequest completeRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId,partETags);

        //断点上传核心方法
        CompleteMultipartUploadResult completeResult = ossClient.completeMultipartUpload(completeRequest);
        return completeResult;
    }

    /**
     * 获取uploadId(即上传到什么位置)
     * @param key
     * @return
     */
    private String claimUploadId(String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }
}
