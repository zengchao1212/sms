package com.github.zengchao1212.sms.service.pab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchao
 * @date 2018/12/19
 */
public class AutoIdentify1 {
    private static int[][] ji = new int[10][];
    private static int[][] xy = new int[10][];

    static {
        init();
    }

    private static void init() {
        ji[0] = new int[]{439,};
        ji[1] = new int[]{-1};
        ji[2] = new int[]{-2,};
        ji[3] = new int[]{486, 491, 492, 503, 516, 520, 527,};
        ji[4] = new int[]{578,};
        ji[5] = new int[]{440, 442, 446, 460, 468, 494, 503,};
        ji[6] = new int[]{487,};
        ji[7] = new int[]{308, 309, 327, 328, 339, 343,};
        ji[8] = new int[]{683, 684, 687,};
        ji[9] = new int[]{511,};
        xy[0] = new int[]{7942,};
        xy[1] = new int[]{-1};
        xy[2] = new int[]{-2,};
        xy[3] = new int[]{7417, 7767, 8279, 8916, 9230, 9267, 9449,};
        xy[4] = new int[]{8765,};
        xy[5] = new int[]{7367, 8382, 8471, 9059, 9205, 9676, 9709,};
        xy[6] = new int[]{7961,};
        xy[7] = new int[]{3071, 3530, 4028, 4661, 4874, 4875,};
        xy[8] = new int[]{10202, 10356, 10426,};
        xy[9] = new int[]{8290,};

    }

    private static void cleanDir() {
        try {
            Process p = Runtime.getRuntime().exec("rm -rf /Users/zengchao/Documents/pab/one/ai/");
            p.waitFor();
            p = Runtime.getRuntime().exec("mkdir /Users/zengchao/Documents/pab/one/ai/");
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        for (int i = -1; i < 10; i++) {
            Path path = Paths.get("/Users/zengchao/Documents/pab/one/ai/" + i);
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        cleanDir();
        File dir = new File("/Users/zengchao/Documents/pab/one/all/");
        int i = 0;
        for (File img : dir.listFiles((d, n) -> n.contains(".bmp"))) {
            BufferedImage image = ImageIO.read(new FileInputStream(img));
            int value = analysis(image);
            Files.copy(Paths.get(img.getAbsolutePath()), Paths.get("/Users/zengchao/Documents/pab/one/ai/", "" + value, img.getName()));
            System.out.println(i++);
        }
//        compute();
//        correct();
    }

    public static void compute() throws IOException {
        InputStream io = new FileInputStream("/Users/zengchao/Documents/pab/one/ai/-1/1545281384188-2.bmp");
        BufferedImage image = ImageIO.read(io);
        int value = analysis(image);
    }

    public static int analysis(BufferedImage image) {
        int jicode = ImgUtil.jizuobiao(image);
        int xycode = ImgUtil.xyzuobiao(image);

        List<Integer> jivs = new ArrayList<>();
        for (int i = 0; i < ji.length; i++) {
            for (int j = 0; j < ji[i].length; j++) {
                if (jicode == ji[i][j]) {
                    jivs.add(i);
                    break;
                }
            }

        }
        List<Integer> xyvs = new ArrayList<>();
        for (int i = 0; i < xy.length; i++) {
            for (int j = 0; j < xy[i].length; j++) {
                if (xycode == xy[i][j]) {
                    xyvs.add(i);
                    break;
                }
            }

        }
        jivs.retainAll(xyvs);
        int value = -1;
        if (jivs.size() == 1) {
            value = jivs.get(0);
        }
        return value;
    }
}
