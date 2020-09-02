package com.github.zengchao1212.sms;

import java.util.concurrent.TimeUnit;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class App {
    public static void main(String[] args) {
//        TaskExecutor.getInstance().addTask("15970037047",100,1, TimeUnit.SECONDS);
        TaskExecutor.getInstance().addTask("18218027494", 10, 20, TimeUnit.SECONDS);
    }
}
