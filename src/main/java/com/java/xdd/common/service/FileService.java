package com.java.xdd.common.service;

import com.java.xdd.common.aliyunoss.PartUploader;

import java.util.List;

public interface FileService{
    void uploadPart(PartUploader partUploader);

    boolean matchUploadPart(PartUploader partUploader);

    boolean completeUploadPart(PartUploader partUploader);

    void upload(PartUploader partUploader);

}
