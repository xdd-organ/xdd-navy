package com.java.xdd.common.aliyunoss;

import java.io.InputStream;

//用与分片式上传
public class PartUploader{
    private InputStream inputStream;//上传的文件
    private long position;//文件是第几个
    private long partSize;//分片大小，除最后一个分片外，其它分片要大于100KB
    private int partNumber;//分片号，范围是1~10,000
    private String uploadId;//Upload ID
    private String uuid;

    public PartUploader(InputStream inputStream, long position, long partSize, int partNumber, String uploadId) {
        this.inputStream = inputStream;
        this.position = position;
        this.partSize = partSize;
        this.partNumber = partNumber;
        this.uploadId = uploadId;
    }
}
