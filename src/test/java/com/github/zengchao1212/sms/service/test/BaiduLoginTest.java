package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.BaiduLogin;
import com.github.zengchao1212.sms.service.SmsBoom;
import org.junit.Test;


/**
 * @author zengchao
 * @date 2018/12/12
 */
public class BaiduLoginTest extends AbstractSmsBoomTest {
    private SmsBoom sender = new BaiduLogin();


    @Test
    public void send() throws InterruptedException {
        send(sender, "15297804323");
    }
}
