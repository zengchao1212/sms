package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.AbcRegister;
import com.github.zengchao1212.sms.service.ChinaMobileLogin;
import com.github.zengchao1212.sms.service.SmsBoom;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;


/**
 * @author zengchao
 * @date 2018/12/12
 */
public class AbcRegisterTest extends AbstractSmsBoomTest{
    private SmsBoom sender=new AbcRegister();


    @Test
    public void send() throws InterruptedException {
        send(sender,"18218044444");
    }
}
