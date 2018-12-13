package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.ChinaMobileLogin;
import com.github.zengchao1212.sms.service.SmsBoom;
import org.junit.Test;


/**
 * @author zengchao
 * @date 2018/12/12
 */
public class ChinaMobileLoginTest extends AbstractSmsBoomTest{
    private SmsBoom sender=new ChinaMobileLogin();

    @Test
    public void send() throws InterruptedException {
        send(sender,"15297444444");
    }
}
