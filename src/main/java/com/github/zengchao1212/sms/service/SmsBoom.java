package com.github.zengchao1212.sms.service;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public interface SmsBoom {
    HttpClient client= BeanFactory.getHttpClient();
    Gson gson=BeanFactory.getGson();
    DefaultResponseHandler responseHandler=new DefaultResponseHandler();
    boolean send(String mobile);
}
