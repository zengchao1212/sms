package com.github.zengchao1212.sms.service.pab;

import java.awt.image.BufferedImage;

/**
 * @author zengchao
 * @date 2018/12/20
 */
public class ImgUtil {

    public static int jizuobiao(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int diagnostic = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int gv = image.getRGB(i, j);
                if (gv == 0xff000000) {
                    double v = Math.sqrt(Math.pow(w / 2, 2) + Math.pow(h / 2, 2)) - Math.sqrt(Math.pow(i - w / 2, 2) + Math.pow(j - h / 2, 2));
                    diagnostic += v;
                }
            }
        }
        return diagnostic;
    }

    public static int xyzuobiao(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int diagnostic = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int gv = image.getRGB(i, j);
                if (gv == 0xff000000) {
                    diagnostic += i * j;
                }
            }
        }
        return diagnostic;
    }
}
