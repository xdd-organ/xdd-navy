package com.java.xdd.common.aliyunoss;

import java.io.Serializable;
import java.util.Date;

//用与分片式上传
public class PartUploader implements Serializable {

    private static final long serialVersionUID = 2885164443553467115L;

    private String md5; //文件的MD5值
    private String id; //文件的id值
    private String name; //文件名称
    private String type; //文件类型
    private Date lastModifiedDate; //文件最后修改时间
    private Long size; //文件大小
    private Integer chunks; //总分片数
    private Integer chunk; //当期分片数
    private Long chunkSize; //分片大小,默认5M

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getChunks() {
        return chunks;
    }

    public void setChunks(Integer chunks) {
        this.chunks = chunks;
    }

    public Integer getChunk() {
        return chunk;
    }

    public void setChunk(Integer chunk) {
        this.chunk = chunk;
    }

    public Long getChunkSize() {
        return chunkSize == null ? 5242880 : chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public String toString() {
        return "PartUploader{" +
                "md5='" + md5 + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", size=" + size +
                ", chunks=" + chunks +
                ", chunk=" + chunk +
                ", chunkSize=" + chunkSize +
                '}';
    }
}
