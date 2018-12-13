package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.ChinaMobile;
import org.junit.Test;


/**
 * @author zengchao
 * @date 2018/12/12
 */
public class ChinaMobileTest {
    private ChinaMobile sender=new ChinaMobile();

    @Test
    public void send() throws InterruptedException {
        int i=0;
        String mobile="15297804323";
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
