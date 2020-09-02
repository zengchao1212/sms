package com.github.zengchao1212.sms.service;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import com.google.gson.Gson;
import org.apache.http.client.HttpClient;

/**
 * @author zengchao
 * @date 2020-09-02
 */
public abstract class AbstractSmsBoom implements SmsBoom {
    protected HttpClient client = BeanFactory.getHttpClient();
    protected Gson gson = BeanFactory.getGson();
    protected DefaultResponseHandler responseHandler = new DefaultResponseHandler();
}
