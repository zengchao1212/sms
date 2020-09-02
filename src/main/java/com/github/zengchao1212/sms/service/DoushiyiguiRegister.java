package com.github.zengchao1212.sms.service;

import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2020-09-02
 */
public class DoushiyiguiRegister extends AbstractSmsBoom {

    @Override
    public boolean send(String mobile) {
        HttpPost req = new HttpPost("http://apionline.doushiyigui.com/member/sendSms");
        HttpEntity httpEntity = new StringEntity(String.format("{\"mobile\":\"%s\",\"type\":0}", mobile),
                ContentType.create("application/json", "UTF-8"));
        req.setEntity(httpEntity);
        String res;
        try {
            res = client.execute(req, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        if (jsonObject.get("code").getAsInt() == 200) {
            return true;
        } else {
            System.out.println(jsonObject.get("msg").getAsString());
        }
        return false;
    }
}
