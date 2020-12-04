package com.jiulingtao.common.utils;

import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片处理
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * 图片设置水印
     * @param srcFile 原图片
     * @param watermarkText 水印文字
     * @param outputStream 输出位置
     * @param originX 水印位置
     * @param originY 水印位置
     */
    public static void watermark(File srcFile, String watermarkText, OutputStream outputStream, int originX, int originY) throws IOException {
        BufferedImage read = ImageIO.read(srcFile);
        Graphics2D graphics = read.createGraphics();
        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置字体类型和大小
        graphics.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        graphics.setColor(new Color(0,255,0));
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.5f)); //设置透明度为0.5
        graphics.drawString(watermarkText, originX, originY);
        graphics.dispose();
        ImageIO.write(read, "png", outputStream);
    }

    /**
     * 图片设置水印
     * @param srcFile 原图片
     * @param watermarkFile 水印图片
     * @param outputStream 输出位置
     * @param originX 水印位置
     * @param originY 水印位置
     */
    public static void watermark(File srcFile, File watermarkFile, OutputStream outputStream, int originX, int originY) throws IOException {
        BufferedImage read = ImageIO.read(srcFile);
        BufferedImage image = ImageIO.read(watermarkFile);
        Graphics2D graphics = read.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,0.5f)); //设置透明度为0.5
        graphics.drawImage(image, originX, originY, null);
        graphics.dispose();
        ImageIO.write(read,"png", outputStream);
    }

    /**
     * 图片缩放
     * @param srcFile 源图片
     * @param outputStream 输出位置
     * @param scale 缩放比例
     */
    public static void scale(File srcFile, OutputStream outputStream, float scale) throws IOException{
        BufferedImage srcImage = ImageIO.read(srcFile);
        BufferedImage out = scale(srcImage, scale);
        ImageIO.write(out, "png", outputStream);
    }

    /**
     * 图片缩放
     * @param srcImage 原图片
     * @param scale 缩放比例
     */
    public static BufferedImage scale(BufferedImage srcImage, float scale) {
        Assert.state(0.0 < scale && scale < 1.0, "缩放比例必须在0~1之间");
        int width = (int) (srcImage.getWidth()*scale);
        int height = (int) (srcImage.getHeight()*scale);
        Image img = srcImage.getScaledInstance(width , height, Image.SCALE_AREA_AVERAGING);
        BufferedImage image = transparentImage(width, height);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(img, 0, 0,null);
        graphics.dispose();
        return image;
    }

    /**
     * 图片裁剪
     * @param srcFile 源文件
     * @param outputStream 输出
     * @param originX 裁剪原点 X（左上角）
     * @param originY 裁剪原点 Y（左上角）
     * @param lengthX 裁剪长度
     * @param lengthY 裁剪高度
     */
    public static void crop(File srcFile, OutputStream outputStream, int originX, int originY, int lengthX, int lengthY) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        BufferedImage outImage = transparentImage(lengthX, lengthY);
        BufferedImage out = crop(srcImage, outImage, originX, originY, lengthX, lengthY);
        ImageIO.write(out, "png", outputStream);
    }

    /**
     * 图片裁剪
     * @param srcImage 源文件
     * @param outImage 输出文件
     * @param originX 裁剪原点 X
     * @param originY 裁剪原点 Y
     * @param lengthX 裁剪宽度
     * @param lengthY 裁剪高度
     */
    public static BufferedImage crop(BufferedImage srcImage, BufferedImage outImage, int originX, int originY, int lengthX, int lengthY) {
        Assert.state(originX >= 0 && originY >= 0, "裁剪原点必须大于0");
        Assert.state(lengthX > 0 && lengthY > 0, "裁剪长宽必须大于0");

        BufferedImage image = srcImage;
        BufferedImage out = outImage;

        int width = lengthX;
        int height = lengthY;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setRGB(x, y,image.getRGB(x + originX, y + originY));
            }
        }
        return out;
    }

    /**
     * 图片切圆角
     * @param srcFile 源文件
     * @param outputStream 输出
     * @param a 圆角大小 第一象限
     * @param b 圆角大小 第二象限
     * @param c 圆角大小 第三象限
     * @param d 圆角大小 第四象限
     */
    public static void fillet(File srcFile, OutputStream outputStream, int a, int b, int c, int d) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        BufferedImage outImage = transparentImage(srcImage.getWidth(), srcImage.getHeight());
        BufferedImage out = fillet(srcImage, outImage, a, b, c, d);
        ImageIO.write(out, "png", outputStream);

    }

    /**
     * 图片切圆角
     * @param srcImage 源图片
     * @param srcImage 输出图片
     * @param a 圆角大小 第一象限
     * @param b 圆角大小 第二象限
     * @param c 圆角大小 第三象限
     * @param d 圆角大小 第四象限
     */
    public static BufferedImage fillet(BufferedImage srcImage, BufferedImage outImage, int a, int b, int c, int d) throws IOException {
        BufferedImage image = srcImage;
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage out = outImage;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int quadrant = quadrant(width, height, x, y); //判断像素在第几象限
                int x1 = 0;
                int y1 = 0;
                int r = 0;
                boolean flag = false;
                switch (quadrant) {
                    case 1: {
                        r = a;
                        flag = x > width - r && y < r;
                        x1 = width - r;
                        y1 = r;
                        break;
                    }
                    case 2: {
                        r = b;
                        flag = x < r && y < r;
                        x1 = r;
                        y1 = r;
                        break;
                    }
                    case 3: {
                        r = c;
                        flag = x < r && y > height - r;
                        x1 = r;
                        y1 = height - r;
                        break;
                    }
                    case 4: {
                        r = d;
                        flag = x > width - r && y > height - r;
                        x1 = width - r;
                        y1 = height - r;
                        break;
                    }
                }
                double sqrt = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
                if (flag && sqrt > r) {
                    out.setRGB(x, y, 0);
                } else {
                    out.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }
        return out;
    }

    /**
     * 新建图片，并设置透明背景
     */
    private static BufferedImage transparentImage(int width, int height) {
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = out.createGraphics(); // 创建图片的画布
        out = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        return out;
    }

    /**
     * 计算x1,y2点所在象限
     */
    private static int quadrant(int x1, int y1, int x2, int y2) {
        int halfX = x1 / 2;
        int halfY = y1 / 2;
        if (x2 < halfX) {
            //2、3象限
            if (y2 < halfY) {
                return 2;
            } else {
                return 3;
            }
        } else {
            //1、4象限
            if (y2 < halfY) {
                return 1;
            } else {
                return 4;
            }
        }
    }
}
