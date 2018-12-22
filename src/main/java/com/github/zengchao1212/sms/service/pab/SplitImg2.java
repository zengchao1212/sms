package com.github.zengchao1212.sms.service.pab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author zengchao
 * @date 2018/12/20
 */
public class SplitImg2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Process p = Runtime.getRuntime().exec("rm -rf /Users/zengchao/Documents/pab/two/all");
            p.waitFor();
            p = Runtime.getRuntime().exec("mkdir -p /Users/zengchao/Documents/pab/two/all");
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        File dir = new File("/Users/zengchao/Documents/pab/vcode/");
        int i = 0;
        for (File img : dir.listFiles((d, n) -> n.contains("gif"))) {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(img));
            BufferedImage b1 = bufferedImage.getSubimage(18, 0, 15, 30);
            wb(b1);
            b1 = qiege(b1);
            String name = img.getName();
            name = name.replace(".gif", "");
            String fullNameChid = name + "-2.bmp";
            String fullName = name + ".bmp";
            ImageIO.write(b1, "bmp", new File("/Users/zengchao/Documents/pab/two/all/" + fullNameChid));
            Files.copy(Paths.get(img.getAbsolutePath()), Paths.get("/Users/zengchao/Documents/pab/two/all/" + fullName));
            System.out.println(i++);
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
