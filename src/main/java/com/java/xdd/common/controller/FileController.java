package com.java.xdd.common.controller;

import com.java.xdd.common.aliyunoss.PartUploader;
import com.java.xdd.common.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("file")
@Controller
@CrossOrigin
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    @Autowired
    private FileService fileService;


    /**
     * 单/多文件普通上传
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(PartUploader partUploader, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            InputStream inputStream = file.getInputStream();
            partUploader.setInputStream(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("------");
        fileService.upload(partUploader);
        return "上传成功！";
    }


    /**
     * 单文件下载
     *
     * @return
     */
    @RequestMapping(value = "/downloadFile")
    @ResponseBody
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        return "fileUpload2";
    }


    /**
     *
     *
     * @return
     */
    @RequestMapping(value = "/test1")
    public String test1(HttpServletRequest request, HttpServletResponse response) {
        return "common/file/fileUpload";
    }


    /**
     * 单文件分片上传
     *
     * @return
     */
    @RequestMapping(value = "/uploadPart")
    @ResponseBody
    public String uploadPart(PartUploader partUploader,@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            InputStream inputStream = file.getInputStream();
            partUploader.setInputStream(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
        fileService.uploadPart(partUploader);


        return "上传成功！";
    }

    /**
     * 单文件分片上传
     *
     * @return
     */
    @RequestMapping(value = "/uploadMorePart")
    @ResponseBody
    public String uploadMorePart(PartUploader[] partUploader,@RequestParam("file") MultipartFile[] file, HttpServletRequest request) {
        try {
            System.out.println("多文件上传");
        } catch (Exception e){
            e.printStackTrace();
        }



        return "上传成功！";
    }

    //匹配分片的md5值，处理重复的上传值
    @RequestMapping(value = "/matchUploadPart")
    @ResponseBody
    public boolean matchUploadPart(PartUploader partUploader){
        return fileService.matchUploadPart(partUploader);
    }

    //所有分片上传完成后，合并文件
    @RequestMapping(value = "/completeUploadPart")
    @ResponseBody
    public boolean completeUploadPart(PartUploader partUploader){
        return fileService.completeUploadPart(partUploader);
    }


}