package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.SmsBoom;

/**
 * @author zengchao
 * @date 2018/12/13
 */
public abstract class AbstractSmsBoomTest {
    protected void send(SmsBoom sender,String mobile) throws InterruptedException {
        int i=0;
        while (true){
            boolean success=sender.send(mobile);
            if(!success){
                break;
            }
            i++;
            System.out.println("发送第"+i+"次");
            Thread.sleep(65*1000);
        }
        System.out.println("共发送"+i+"次");
    }
}
