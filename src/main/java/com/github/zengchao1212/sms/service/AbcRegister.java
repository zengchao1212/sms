package com.github.zengchao1212.sms.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2018/12/13
 */
public class AbcRegister extends AbstractSmsBoom {
    private void init() throws IOException {
        HttpGet req=new HttpGet("https://perbank.abchina.com/EbankSite/SelfHelpRegisterInitAct.do");
        client.execute(req,responseHandler);
    }
    private boolean sendSmsVerifyCodeAct(String mobile) throws IOException {
        init();
        HttpPost req=new HttpPost("https://perbank.abchina.com/EbankSite/SendSmsVerifyCodeAct.ebf");
        req.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        HttpEntity httpEntity = new StringEntity(String.format("mobileNoField=mobile&sendType=sgsrfdx008&mobile=%s", mobile),
                ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
        req.setEntity(httpEntity);
        String res=client.execute(req,responseHandler);
        String htmlTitle;
        if(res.isEmpty()){
            return true;
        }else if((htmlTitle=Jsoup.parse(res).title()).isEmpty()){
            System.err.println(res);
        }else {
            System.out.println(htmlTitle);
        }
        return false;
    }

    @Override
    public boolean send(String mobile){
        try {
            return sendSmsVerifyCodeAct(mobile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
