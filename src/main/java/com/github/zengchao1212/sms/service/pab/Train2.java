package com.github.zengchao1212.sms.service.pab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zengchao
 * @date 2018/12/19
 */
public class Train2 {

    public static void main(String[] args) throws IOException {

//        jizuobiao(0);
//        jizuobiao(3);
        jizuobiao(4);
//        jizuobiao(5);
//        jizuobiao(6);
//        jizuobiao(7);
//        jizuobiao(8);
//        jizuobiao(9);

//        xyzuobiao(0);
//        xyzuobiao(3);
        xyzuobiao(4);
//        xyzuobiao(5);
//        xyzuobiao(6);
//        xyzuobiao(7);
//        xyzuobiao(8);
//        xyzuobiao(9);
    }

    public static void jizuobiao(int num) throws IOException {
        File dir = new File("/Users/zengchao/Documents/pab/two/sign/" + num);
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Map<Integer, AtomicInteger> map = new TreeMap<>(Comparator.comparingInt(k -> k));
        for (File img : dir.listFiles((d, name) -> name.contains("bmp"))) {
            InputStream in = new FileInputStream(img);
            BufferedImage bufferedImage = ImageIO.read(in);
            int v = ImgUtil.jizuobiao(bufferedImage);
            AtomicInteger inc = map.putIfAbsent(v, new AtomicInteger(1));
            if (inc != null) {
                inc.incrementAndGet();
            }
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }
        System.out.println(String.format("%d %d %d", num, min, max));
        map.forEach((k, v) -> System.out.print(k + ","));
        System.out.println();
        System.out.println();
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    public static void xyzuobiao(int num) throws IOException {
        File dir = new File("/Users/zengchao/Documents/pab/two/sign/" + num);
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        Map<Integer, AtomicInteger> map = new TreeMap<>(Comparator.comparingInt(k -> k));
        for (File img : dir.listFiles((d, name) -> name.contains("bmp"))) {
            InputStream in = new FileInputStream(img);
            BufferedImage bufferedImage = ImageIO.read(in);
            int v = ImgUtil.xyzuobiao(bufferedImage);
            AtomicInteger inc = map.putIfAbsent(v, new AtomicInteger(1));
            if (inc != null) {
                inc.incrementAndGet();
            }
            if (v < min) {
                min = v;
            }
            if (v > max) {
                max = v;
            }
        }
        System.out.println(String.format("%d %d %d", num, min, max));
        map.forEach((k, v) -> System.out.print(k + ","));
        System.out.println();
        System.out.println();
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}
