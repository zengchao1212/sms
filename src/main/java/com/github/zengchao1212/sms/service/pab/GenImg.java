package com.github.zengchao1212.sms.service.pab;

import com.github.zengchao1212.sms.BeanFactory;
import com.github.zengchao1212.sms.service.response.handler.DefaultResponseHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author zengchao
 * @date 2018/12/20
 */
public class GenImg {
    public static void main(String[] args) throws IOException {
        try {
            Runtime.getRuntime().exec("rm -rf /Users/zengchao/Documents/pab/vcode/");
            Runtime.getRuntime().exec("mkdir /Users/zengchao/Documents/pab/vcode/");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        HttpClient client = BeanFactory.getHttpClient();
        Gson gson = BeanFactory.getGson();
        DefaultResponseHandler responseHandler = new DefaultResponseHandler();
        for (int i = 0; i < 10000; i++) {
            HttpPost req = new HttpPost("https://bank.pingan.com.cn/rmb/brcp/uc/cust/uc-core.get-validCode.do");
            HttpEntity entity = new StringEntity("channelType=d&responseDataType=JSON&channelId=netbank-pc&deviceType=macos&type=1", ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
            req.setEntity(entity);
            String res = client.execute(req, responseHandler);
            JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
            if (jsonObject.get("responseCode").getAsString().equals("000000")) {
                String base64 = jsonObject.getAsJsonObject("image").get("validImage").getAsString();
                int index = base64.indexOf(",");
                base64 = base64.substring(index + 1);
                byte[] data = Base64.decodeBase64(base64);
                long cur = System.currentTimeMillis();
                Files.write(Paths.get("/Users/zengchao/Documents/pab/vcode/" + cur + ".gif"), data, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);

            } else {
                System.err.println(jsonObject.get("responseCode").getAsString());
            }
            System.out.println(i);
        }

    }
}
