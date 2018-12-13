package com.github.zengchao1212.sms.test;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class HttpClientTest {
    private HttpClient client= BeanFactory.getHttpClient();

    @After
    public void stop() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    public void t1() throws IOException {
        HttpPost req=new HttpPost("https://login.10086.cn/sendRandomCodeAction.action");
        req.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        req.setHeader("X-Requested-With","XMLHttpRequest");
        req.setHeader("Cookie","sendflag=20181213125719157747");
        req.setHeader("Xa-before","08995602622720652693118540497249");
        HttpEntity httpEntity=new StringEntity("userName=18218027494&type=01&channelID=12003");
        req.setEntity(httpEntity);
        String res=client.execute(req,new DefaultResponseHandler());
        System.out.println(res);
    }

    @Test
    public void common(){
        String s="userName=18218027494&type=01&channelID=12034";
        System.out.println(s.length());
    }
}
