package com.github.zengchao1212.sms.test;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class HttpClientTest {
    private HttpClient client= BeanFactory.getHttpClient();

    @Test
    public void t1() throws IOException {
//        HttpGet req=new HttpGet("https://epass.icbc.com.cn/resetpwd/reset_pwd_index.jsp?StructCode=1&encryptedData=w9A3onPU54b7Lq9Du2GSCLs8Fpe9sVkrmFZHbpyAf74JsR6gEJL8ONaW1btfSEuQ7VZKRFqR6osRRr2cbm2XcEMBgjny0OOiSRhxSFBbuxRRV4vtmX9Yx%2BvmAeqXB3ovaUh/pe9zulPVlFtx%2BqoR2czSnlcpqSHdRjGz8UaOW69r/70YBfOn0CPyVMaYcOVAd7cJbWx%2B8g3OZYYLzkofLo38v%2BXI3qG3r4O%2Bc9yj1bKDJiYeNRGV4ZdGCnHhsu0hgM/rhHRe25tWjjXXXKLThYhH%2BEtyjlovZTaRIN0Q9S1u1PoqEX3b2D2WC%2BsWOUBoVOHtTIT66nR6zjghWDRUBRaOWgCymHBey9LxoZBQHt4fPxPpejeFNJpHaU7VDkggWQD7PZqclCfbVho7WFutMqxxDNt5wgEpw8Cz7ssPCDk=%2B");
        HttpGet req = new HttpGet("http://4jt4ex.natappfree.cc//flash/controller/Test/xxx");
        String res=client.execute(req,new DefaultResponseHandler());
        System.out.println(res);
    }

    @Test
    public void common() throws IOException {
    }

}
