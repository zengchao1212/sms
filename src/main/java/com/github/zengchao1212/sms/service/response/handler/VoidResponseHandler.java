package com.github.zengchao1212.sms.service.response.handler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zengchao
 * @date 2018/12/13
 */
public class VoidResponseHandler implements ResponseHandler<Void> {
    @Override
    public Void handleResponse(HttpResponse response) throws IOException {
        if(response.getStatusLine().getStatusCode()!=200){
            throw new ClientProtocolException(response.getStatusLine().toString());
        }
        try (InputStream in=response.getEntity().getContent()){
            byte[] buffer=new byte[1024];
            while (in.read(buffer)!=-1){

            }
        }
        return null;
    }
}
