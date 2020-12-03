package com.jiulingtao.common.utils;

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
     *
     * @param srcFile 源文件
     * @param outputStream 输出
     * @param a 圆角大小
     * @param b 圆角大小
     * @param c 圆角大小
     * @param d 圆角大小
     */
    public static void fillet(File srcFile, OutputStream outputStream, int a, int b, int c, int d) throws IOException {
        BufferedImage image = ImageIO.read(srcFile);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = out.createGraphics();
        out = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();

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
        ImageIO.write(out, "png", outputStream);
    }

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
