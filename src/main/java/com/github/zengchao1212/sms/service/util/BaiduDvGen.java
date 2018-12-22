package com.github.zengchao1212.sms.service.util;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BaiduDvGen {
    private static LinkedHashMap<String, Object> global = new LinkedHashMap<>();
    private static LinkedHashMap<String, Object> e = new LinkedHashMap<>();

    public static String gen() {

        e.put("mouseDown", "");
        e.put("keyDown", "");
        e.put("mouseMove", "767,465,678062,|");
        e.put("version", 26);
        long cur = System.currentTimeMillis();
        double random = Math.random();
        e.put("loadTime", ((double) cur + 2) / 1000);
        e.put("browserInfo", "3,2,70");
        String token = "tk" + random + cur;
        e.put("token", token);
        e.put("location", "https://www.baidu.com/,undefined");
        e.put("screenInfo", "23,119,2264,601,2560,1440,2560,2264,1337");
        e.put("flashInfo", null);

        return token + "@" + S(e, token);
    }

    static char[] o(int[][] e) {
        List<Character> t = new ArrayList<>();
        for (int n = 0; n < e.length; n++) {
            for (int r = e[n][0]; r <= e[n][1]; r++) {
                t.add((char) r);
            }
        }
        char[] array = new char[t.size()];
        for (int i = 0; i < t.size(); i++) {
            array[i] = t.get(i);
        }
        return array;
    }

    static char[] r(char[] e, String t) {
        String[] n = t.split("");
        for (int r = 0; r < e.length; r++) {
            int o = r % n.length;
            o = n[o].charAt(0);
            o %= e.length;
            char a = e[r];
            e[r] = e[o];
            e[o] = a;
        }
        return e;
    }

    /**
     * @param e data
     * @param t token
     */
    static String S(LinkedHashMap<String, Object> e, String t) {
        n r = new n(t);
        LinkedHashMap<String, Integer> o = new LinkedHashMap<>();
        o.put("flashInfo", 0);
        o.put("mouseDown", 1);
        o.put("keyDown", 2);
        o.put("mouseMove", 3);
        o.put("version", 4);
        o.put("loadTime", 5);
        o.put("browserInfo", 6);
        o.put("token", 7);
        o.put("location", 8);
        o.put("screenInfo", 9);
//
        StringBuilder a = new StringBuilder(r.iary(new int[]{2}));
//        e.forEach();
        e.forEach((i, d) -> {
            if (o.get(i) != 0) {
                int c;
                if (d instanceof Number) {
                    c = (((Number) d).intValue() >= 0 ? 1 : 2);
                    d = r._int(((Number) d).intValue());

                } else if (d instanceof Boolean) {
                    c = 3;
                    d = r._int(((boolean) d) ? 1 : 0);
                } else if (d.getClass().isArray()) {
                    c = 4;
                    d = r.bary((int[]) d);
                } else {
                    c = 0;
                    d = r.str(d.toString() + "");
                }

                if (!((String) d).isEmpty()) {
                    a.append(r.iary(new int[]{o.get(i), c, ((String) d).length()}) + d);
                }
            }
        });
        return a.toString();
    }

    static String a(int e, char[] t) {
        String n = "";
        int r = Math.abs(e);
        if (r != 0) {
            for (; r != 0; ) {
                n += String.valueOf(t[r % t.length]);
                r = r / t.length;
            }

        } else {
            n = String.valueOf(t[0]);
        }

        return n;
    }

    void z() {
        //        return W() ? void I() : setTimeout(I, 20)
    }

    boolean W() {
//        t.getElementById("dv_Input")
        return false;
    }

    void I() {
        LinkedHashMap<String, Object> w = new LinkedHashMap<>();
        w.put("mouseDown", "");
        w.put("keyDown", "");
        w.put("mouseMove", "");
        w.put("", "");
        w.put("", "");
        w.put("", "");
        w.put("", "");
        w.put("", "");
        w.put("", "");
        w.put("", "");
//        construct data
    }

    static class n {
        private String token;

        public n(String e) {
            int[][] t = new int[][]{{48, 57}, {65, 90}, {97, 122}, {45, 45}, {126, 126}};
            char[] n = o(t);
            //t.slice(1)
            char[] a = o(new int[][]{{65, 90}, {97, 122}, {45, 45}, {126, 126}});
            n = r(n, e);
            a = r(a, e);
            global.put("dict", n);
            global.put("dict2", a);
        }

        String _int(int e) {
            return a(e, (char[]) global.get("dict"));
        }

        String iary(int[] e) {
            String t = "";
            for (int n = 0; n < e.length; n++) {
                String r = a(e[n], (char[]) global.get("dict2"));
                t += r.length() > 1 ? r.length() - 2 + r : r;
            }
            return t;
        }

        String bary(int[] e) {
            int t = 0;
            LinkedHashMap<String, Boolean> n = new LinkedHashMap<>();
            for (int r = 0; r < e.length; r++) {
                if (e[r] > t) {
                    t = e[r];
                    n.put(String.valueOf(e[r]), true);
                }
            }
            int o = t / 6;
            o += (t % 6) != 0 ? 1 : 0;
            String a = "";
            for (int r = 0; o > r; r++) {
                int d = 0, c = 0;
                for (int i = 6 * r; 6 > c; c++) {
                    if (n.get(String.valueOf(i))) {
                        d += Math.pow(2, c);
                        i++;
                    }
                }
                a += String.valueOf(((char[]) global.get("dict"))[d]);
            }
            return a;
        }

        String str(String e) {
            List<Integer> t = new ArrayList<>();
            for (int n = 0; n < e.length(); n++) {
                char r = e.charAt(n);
                if (r >= 1 && 127 >= r) {
                    t.add((int) r);
                } else if (r > 2047) {
                    t.add(224 | r >> 12 & 15);
                    t.add(128 | r >> 6 & 63);
                    t.add(128 | r >> 0 & 63);
                } else {
                    t.add(192 | r >> 6 & 31);
                    t.add(128 | r >> 0 & 63);
                }

            }
            String o = "";
            char[] dict = (char[]) global.get("dict");
            for (int n = 0, a = t.size(); a > n; ) {
                int i = t.get(n++);
                if (n >= a) {
                    o += dict[i >> 2];
                    o += dict[(3 & i) << 4];
                    o += "__";
                    break;
                }
                int d = t.get(n++);
                if (n >= a) {
                    o += dict[i >> 2];
                    o += dict[(3 & i) << 4 | (240 & d) >> 4];
                    o += dict[(15 & d) << 2];
                    o += "_";
                    break;
                }
                int c = t.get(n++);
                o += dict[i >> 2];
                o += dict[(3 & i) << 4 | (240 & d) >> 4];
                o += dict[(15 & d) << 2 | (192 & c) >> 6];
                o += dict[63 & c];
            }
            return o;
        }
    }
}
