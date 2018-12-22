package com.github.zengchao1212.sms;

import com.github.zengchao1212.sms.service.AbcRegister;
import com.github.zengchao1212.sms.service.IcbcForgetPwd;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class App {

    public static void main(String[] args) {
        final AbcRegister abcRegister = new AbcRegister();
        final IcbcForgetPwd icbcForgetPwd = new IcbcForgetPwd();
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean f = abcRegister.send("15297804323");
                if (f) {
                    System.out.println("abc发送成功");
                }
                try {
                    Thread.sleep(65 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "abc-sender");
        Thread t2 = new Thread(() -> {
            while (true) {
                boolean f = icbcForgetPwd.send("15297804323");
                if (f) {
                    System.out.println("icbc发送成功");
                }
                try {
                    Thread.sleep(65 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "icbc-sender");
        t1.start();
        t2.start();
    }
}
