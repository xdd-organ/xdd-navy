package com.java.xdd.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.xdd.common.aliyunoss.PartUploader;
import com.java.xdd.common.service.FileService;
import com.java.xdd.common.service.RedisService;
import com.java.xdd.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service("fileService")
public class FileServiceImpl implements FileService{

    private ObjectMapper mapper = new ObjectMapper();
    private JSONObject jsonObject = new JSONObject();
    // 图片位置
    @Value(value = "${imgPath}")
    private String imgPath;

    private String lock = "lock";

    @Autowired
    private RedisService redisService;

    //上传文件到本地
    @Override
    public void uploadPart(PartUploader partUploader){

        String filepath = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMMdd");

        File file = new File(imgPath + File.separator + filepath);
        if (!file.exists()) file.mkdirs(); //判断该文件夹是否存在

        String tempFile = file.getAbsolutePath() + File.separator + this.getFileName() + partUploader.getName().substring(partUploader.getName().lastIndexOf("."));
        partUploader.setTempPath(tempFile);//分片文件的临时路径

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = partUploader.getInputStream();//分片文件的输入流
            outputStream = new FileOutputStream(new File(tempFile));//分片文件存放的位置
            this.inputStreamToOutputStream(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null)inputStream.close();
                if (outputStream != null)outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        synchronized (lock) {
            String md5Encrypt = partUploader.getMd5Encrypt();
            if (redisService.exists(md5Encrypt)) {
                redisService.hset(md5Encrypt, partUploader.getChunkMd5Encrypt(), JSONObject.toJSONString(partUploader));
            } else {
                redisService.hset(md5Encrypt, partUploader.getChunkMd5Encrypt(), JSONObject.toJSONString(partUploader)); //使用redis储存分片上传信息
            }
        }
        List<String> chunkMap = redisService.hvals(partUploader.getMd5Encrypt());


        System.out.println(chunkMap.size() + "-------" + partUploader);
        if (chunkMap.size() == partUploader.getChunks()) {
            System.out.println(chunkMap);
            List<PartUploader> uploaderList = this.sortString(chunkMap);
            System.out.println(uploaderList);
            try {
                this.mergeFile(uploaderList, imgPath + File.separator + filepath + File.separator + partUploader.getName());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            System.out.println("执行合并完成！");
            redisService.del(partUploader.getMd5Encrypt());
        }

    }

    @Override
    public boolean matchUploadPart(PartUploader partUploader) {
        if (partUploader ==null || StringUtils.isEmpty(partUploader.getChunkMd5Encrypt())) return false;
        Map<String, String> map = redisService.hgetAll(partUploader.getMd5Encrypt());
        if (map == null || map.isEmpty()) return false;
        return map.containsKey(partUploader.getChunkMd5Encrypt());
    }

    /**
     * 合并文件
     * @param partUploader
     * @return
     */
    @Override
    public boolean completeUploadPart(PartUploader partUploader) {
        return false;
    }

    @Override
    public void upload(PartUploader partUploader) {
        String filepath = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMMdd");

        File file = new File(imgPath + File.separator + filepath);
        if (!file.exists()) file.mkdirs(); //判断该文件夹是否存在

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = partUploader.getInputStream();//分片文件的输入流
            outputStream = new FileOutputStream(new File(imgPath + File.separator + filepath + File.separator + partUploader.getName()));//分片文件存放的位置
            this.inputStreamToOutputStream(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null)inputStream.close();
                if (outputStream != null)outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    //获取临时文件名称
    private synchronized String getFileName(){
        return System.currentTimeMillis() + "";
    }

    //输入流转输出流
    private void inputStreamToOutputStream(InputStream inputStream, OutputStream outputStream) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        byte[] bytes = new byte[1024 * 1024];
        int len;
        while ((len = (bis.read(bytes))) > 0) {
            bos.write(bytes, 0, len);
        }
    }

    //排序字符串，并转成Integer
    private List<PartUploader> sortString(List<String> chunkList){
        List<PartUploader> uploaderList = new ArrayList<>();
        for (String chunk : chunkList) {
            uploaderList.add(JSONObject.parseObject(chunk, PartUploader.class));//json字符串转PartUploader对象
        }
        Collections.sort(uploaderList, new Comparator<PartUploader>() {
            @Override
            public int compare(PartUploader o1, PartUploader o2) {
                return o1.getChunk() - o2.getChunk();//排序
            }
        });
        return uploaderList;
    }

    //合并文件
    private void mergeFile(List<PartUploader> chunkList, String path) throws IOException{
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            for (PartUploader s : chunkList) {
                File file = new File(s.getTempPath());
                InputStream inputStream = new FileInputStream(file);
                try {
                    byte[] bytes = new byte[1024 * 1024];
                    int len;
                    while ((len = (inputStream.read(bytes))) > 0) {
                        outputStream.write(bytes, 0, len);
                    }
                } finally {
                    inputStream.close();
                }
                file.delete();//删除分片文件
            }
        } finally {
             if (outputStream != null) outputStream.close();
        }
    }

}
