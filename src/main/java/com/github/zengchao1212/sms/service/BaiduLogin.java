package com.github.zengchao1212.sms.service;

import com.github.zengchao1212.sms.service.util.BaiduDvGen;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2018/12/14
 */
public class BaiduLogin extends AbstractSmsBoom {

    private String callbackFunction = "bd__cbs__cexqg5";

    private void loadCookies() throws IOException {
        HttpGet req = new HttpGet("https://www.baidu.com/");
        client.execute(req, responseHandler);
//        req = new HttpGet("https://passport.baidu.com/v2/?login");
//        client.execute(req, responseHandler);
    }

    private String getGID() {
        return "FDE4145-A964-4B55-818C-B0AA00FB3737";
    }

    private String getToken(String gid) throws IOException {
        loadCookies();
        String url = String.format("https://passport.baidu.com/v2/api/?getapi&tpl=mn&apiver=v3&tt=%d&class=login&gid=%s&loginversion=v4&logintype=dialogLogin",
                System.currentTimeMillis(), gid);
        HttpGet req = new HttpGet(url);
        String res = client.execute(req, responseHandler);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        return jsonObject.getAsJsonObject("data").get("token").getAsString();
    }

    private int phoneStatus(String mobile, String token, String gid) throws IOException {
        String url = String.format("https://passport.baidu.com/v2/?getphonestatus&token=%s&tpl=mn&apiver=v3&tt=%d&gid=%sE&phone=%s&loginversion=v4&countrycode=&traceid=&callback=%s",
                token, System.currentTimeMillis(), gid, mobile, callbackFunction);
        HttpGet req = new HttpGet(url);
        String res = client.execute(req, responseHandler);
        res = res.substring(callbackFunction.length() + 1, res.length() - 1);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        int status = jsonObject.getAsJsonObject("errInfo").get("no").getAsInt();
        return status;

    }

    private String dv() {
        return BaiduDvGen.gen();
    }

    private boolean sendPwd(String mobile, String token, String gid) throws IOException {
        String url = String.format("https://passport.baidu.com/v2/api/senddpass?gid=%s&username=%s&countrycode=&bdstoken=%s&tpl=mn&loginVersion=v4&flag_code=0&client=&dv=%s&apiver=v3&tt=%d&traceid=&callback=%s",
                gid, mobile, token, dv(), System.currentTimeMillis(), callbackFunction);
        HttpGet req = new HttpGet(url);
        String res = client.execute(req, responseHandler);
        res = res.substring(callbackFunction.length() + 1, res.length() - 1);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        if (jsonObject.get("errno").getAsInt() == 0) {
            return true;
        } else {
            System.err.println(jsonObject.get("msg").getAsString());
            return false;
        }
    }

    @Override
    public boolean send(String mobile) {

        try {
            String gid = getGID();
            String token = getToken(gid);
            int status = phoneStatus(mobile, token, gid);
            if (status == 0) {
                return sendPwd(mobile, token, gid);
            } else {
                System.err.println("手机号码未注册");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
