package com.github.zengchao1212.sms.service;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import com.github.zengchao1212.sms.service.response.handler.VoidResponseHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class ChinaMobile implements SmsBoom{
    private HttpClient client= BeanFactory.getHttpClient();
    private Gson gson=BeanFactory.getGson();
    private final DefaultResponseHandler responseHandler=new DefaultResponseHandler();

    private void sendFlag() throws IOException {
        HttpGet req=new HttpGet("https://login.10086.cn/sendflag.htm?timestamp="+System.currentTimeMillis());
        client.execute(req,responseHandler);
    }

    private String loadToken(String mobile) throws IOException {
        sendFlag();
        HttpPost req=new HttpPost("https://login.10086.cn/loadToken.action");
        req.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        HttpEntity httpEntity=new StringEntity(String.format("userName=%s",mobile));
        req.setEntity(httpEntity);
        String content=client.execute(req,responseHandler);
        JsonObject jsonObject=gson.fromJson(content, JsonObject.class);
        if(!jsonObject.get("code").getAsString().equals("0000")){
            throw  new RuntimeException(content);
        }
        return jsonObject.get("result").getAsString();
    }
    private boolean sendRandomCodeAction(String mobile) throws IOException {
        Map<String,String> errorCodeMap=new HashMap<>();
        errorCodeMap.put("0","成功");
        errorCodeMap.put("1","对不起，短信随机码暂时不能发送，请一分钟以后再试！");
        errorCodeMap.put("2","对不起，短信随机码获取达到上限！");
        errorCodeMap.put("3","对不起，短信发送次数过于频繁！");
        errorCodeMap.put("4","对不起，渠道code不能为空！");
        errorCodeMap.put("5","对不起，渠道code异常！");
        errorCodeMap.put("6","发送短信验证码失败,请检查!");
        errorCodeMap.put("4005","手机号码有误，请重新输入!");
        HttpPost req=new HttpPost("https://login.10086.cn/sendRandomCodeAction.action");
        req.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        req.addHeader("X-Requested-With","XMLHttpRequest");
        req.addHeader("Xa-before",loadToken(mobile));
        HttpEntity httpEntity=new StringEntity(String.format("userName=%s&type=01&channelID=12003",mobile));
        req.setEntity(httpEntity);
        String res=client.execute(req,responseHandler);
        if(!res.equals("0")){
            System.err.println(errorCodeMap.get(res));
        }
        return res.equals("0");
    }
    public boolean send(String mobile){
        try {
            return sendRandomCodeAction(mobile);
        } catch (IOException e) {
            return false;
        }
    }
}
