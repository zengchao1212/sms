package com.github.zengchao1212.sms.service.pab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zengchao
 * @date 2018/12/20
 */
public class SplitImg22 {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Process p = Runtime.getRuntime().exec("rm -rf /Users/zengchao/Documents/pab/two/alli");
            p.waitFor();
            p = Runtime.getRuntime().exec("mkdir -p /Users/zengchao/Documents/pab/two/alli");
            p.waitFor();
            for (int i = -1; i < 10; i++) {
                Path path = Paths.get("/Users/zengchao/Documents/pab/two/alli/" + i);
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        File dir = new File("/Users/zengchao/Documents/pab/vcode/");
        int i = 0;
        for (File img : dir.listFiles((d, n) -> n.contains("gif"))) {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(img));
            BufferedImage b1 = bufferedImage.getSubimage(2, 0, 15, 30);
            wb(b1);
            b1 = qiege(b1);
            int value = AutoIdentify1.analysis(b1);
            BufferedImage b2;
            if (value == 0) {
                b2 = bufferedImage.getSubimage(20, 0, 13, 30);
            } else if (value == 3) {
                b2 = bufferedImage.getSubimage(16, 0, 15, 30);
            } else if (value == 4) {
                b2 = bufferedImage.getSubimage(20, 0, 13, 30);
            } else if (value == 5) {
                b2 = bufferedImage.getSubimage(15, 0, 16, 30);
            } else if (value == 6) {
                b2 = bufferedImage.getSubimage(20, 0, 13, 30);
            } else if (value == 7) {
                b2 = bufferedImage.getSubimage(16, 0, 15, 30);
            } else if (value == 8) {
                b2 = bufferedImage.getSubimage(18, 0, 15, 30);
            } else if (value == 9) {
                b2 = bufferedImage.getSubimage(20, 0, 14, 30);
            } else {
                b2 = bufferedImage.getSubimage(18, 0, 15, 30);
            }
            wb(b2);
            b2 = qiege(b2);
            String name = img.getName();
            name = name.replace(".gif", "");
            String fullNameChid = name + "-2.bmp";
            String fullName = name + ".bmp";
            ImageIO.write(b2, "bmp", new File("/Users/zengchao/Documents/pab/two/alli/" + value + "/" + fullNameChid));
            Files.copy(Paths.get(img.getAbsolutePath()), Paths.get("/Users/zengchao/Documents/pab/two/alli/" + value + "/" + fullName));
            System.out.println(i++);
            if (i > 300) {
                break;
            }
        }
    }

    public static void wb(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if ((image.getRGB(x, y) & 0xff) > 122) {
                    image.setRGB(x, y, 0xffffff);
                } else {
                    image.setRGB(x, y, 0);
                }
            }
        }
    }

    public static BufferedImage qiege(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int start_y = 0;
        int end_y = 0;
        lable1:
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (image.getRGB(x, y) != -1) {
                    start_y = y;
                    break lable1;
                }
            }
        }
        lable2:
        for (int y = h - 1; y > -1; y--) {
            for (int x = 0; x < w; x++) {
                if (image.getRGB(x, y) != -1) {
                    end_y = y;
                    break lable2;
                }
            }
        }
        return image.getSubimage(0, start_y, w, end_y - start_y + 1);
    }
}
