package com.github.zengchao1212.sms.service.test;

import com.github.zengchao1212.sms.service.IcbcForgetPwd;
import com.github.zengchao1212.sms.service.SmsBoom;
import org.junit.Test;


/**
 * @author zengchao
 * @date 2018/12/12
 */
public class IcbcForgetPwdTest extends AbstractSmsBoomTest {
    private SmsBoom sender = new IcbcForgetPwd();


    @Test
    public void send() throws InterruptedException {
        send(sender, "18218027494");
    }
}
