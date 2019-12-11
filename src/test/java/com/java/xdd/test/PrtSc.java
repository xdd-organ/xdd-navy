package com.java.xdd.es.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author xdd
 * @date 2019/12/11
 */
public class PrtSc {

    public static void main(String[] args)  throws Exception{
        for (int i = 0; i < 5; i++) {
            test3( i + "img.png");
            Thread.sleep(3000);
        }
    }

    public static void test3(String name) {
        // 获取截图的大小
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            // java自动化类
            Robot robot = new Robot();
            // 创建输出
            OutputStream out = new FileOutputStream(new File("E:\\移动\\log\\img\\" + name));
            // 截图
            BufferedImage image = robot.createScreenCapture(screenRect);
            // 保存为gif
            ImageIO.write(image, "gif", out);
            System.out.println("ok...");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
