package com.java.xdd.common.service;

import com.java.xdd.common.aliyunoss.PartUploader;

public interface FileService{
    void uploadPart(PartUploader partUploader);

    boolean matchUploadPart(PartUploader partUploader);

    boolean completeUploadPart(PartUploader partUploader);
}
