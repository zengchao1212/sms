package com.github.zengchao1212.sms.service;

import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zengchao
 * @date 2018/12/13
 */
public class IcbcForgetPwd extends AbstractSmsBoom {
    Pattern p1 = Pattern.compile("sendParam\\s*\\[\"tranFlag\"\\]\\s*=\\s*\"(.*)\"");
    Pattern p2 = Pattern.compile("sendParam\\s*\\[\"SessionId\"\\]\\s*=\\s*\"(.*)\"");
    Pattern p3 = Pattern.compile("sendParam\\s*\\[\"tranCode\"\\]\\s*=\\s*\"(.*)\"");
    Pattern p4 = Pattern.compile("sendParam\\s*\\[\"StructCode\"\\]\\s*=\\s*\"(.*)\"");

    private String init() throws IOException {
        HttpGet req = new HttpGet("https://mybank.icbc.com.cn/icbc/newperbank/perbank3/frame/frame_index.jsp");
        String res = client.execute(req, responseHandler);
        Elements elements = Jsoup.parse(res).select("a");
        for (Element element : elements) {
            String href = element.attr("href");
            if (href.contains("regist_index.jsp")) {
                int index = href.indexOf("regist_index.jsp?");
                if (index == -1) {
                    return "";
                } else {
                    String params = href.substring(index + "regist_index.jsp?".length());
                    params = params.replaceAll("&amp;", "&");
                    params = params.replaceAll("\r", "");
                    params = params.replaceAll("\n", "");
                    return params;
                }

            }
        }
        return "";
    }

    private String resetPwd() throws IOException {
        String params = init();
        HttpGet req = new HttpGet("https://epass.icbc.com.cn/resetpwd/reset_pwd_index.jsp?" + params);
        String res = client.execute(req, responseHandler);
        params = "";
        Document document = Jsoup.parse(res);
        Element tranFlag = document.selectFirst("input[name=tranFlag]");
        params += "tranFlag=" + (tranFlag == null ? "" : tranFlag.val());
        Element netType = document.selectFirst("input[name=netType]");
        params += "&netType=" + (netType == null ? "" : netType.val());
        Element Language = document.selectFirst("input[name=Language]");
        params += "&Language=" + (Language == null ? "" : Language.val());
        Element PlatFlag = document.selectFirst("input[name=PlatFlag]");
        params += "&PlatFlag=" + (PlatFlag == null ? "" : PlatFlag.val());
        Element ComputID = document.selectFirst("input[name=ComputID]");
        params += "&ComputID=" + (ComputID == null ? "" : ComputID.val());
        Element encryptedData = document.selectFirst("input[name=encryptedData]");
        params += "&encryptedData=" + (encryptedData == null ? "" : encryptedData.val());
        Element remoteAddr = document.selectFirst("input[name=remoteAddr]");
        params += "&remoteAddr=" + (remoteAddr == null ? "" : remoteAddr.val());
        Element StructCode = document.selectFirst("input[name=StructCode]");
        params += "&StructCode=" + (StructCode == null ? "" : StructCode.val());
        Element customerGroup = document.selectFirst("input[name=customerGroup]");
        params += "&customerGroup=" + (customerGroup == null ? "" : customerGroup.val());
        Element channelCode = document.selectFirst("input[name=channelCode]");
        params += "&channelCode=" + (channelCode == null ? "" : channelCode.val());
        Element User_browser = document.selectFirst("input[name=User_browser]");
        params += "&User_browser=" + (User_browser == null ? "" : User_browser.val());
        Element User_os = document.selectFirst("input[name=User_os]");
        params += "&User_os=" + (User_os == null ? "" : User_os.val());
        return params;
    }

    private String createSession() throws IOException {
        String params = resetPwd();
        HttpPost req = new HttpPost("https://epass.icbc.com.cn/servlet/ICBCINBSEstablishSessionServlet");
        HttpEntity entity = new StringEntity(params, ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
        req.setEntity(entity);
        String res = client.execute(req, responseHandler);
        String tranFlag = "";
        String SessionId = "";
        String tranCode = "";
        String StructCode = "";
        Matcher m1 = p1.matcher(res);

        while (m1.find()) {
            tranFlag = m1.group(1);
            break;
        }
        m1 = p2.matcher(res);
        while (m1.find()) {
            SessionId = m1.group(1);
            break;
        }
        m1 = p3.matcher(res);
        while (m1.find()) {
            tranCode = m1.group(1);
            break;
        }
        m1 = p4.matcher(res);
        while (m1.find()) {
            StructCode = m1.group(1);
            break;
        }
        return String.format("tranFlag=%s&SessionId=%s&tranCode=%s&StructCode=%s", tranFlag, SessionId, tranCode, StructCode);
    }

    private boolean asynGetDataServlet(String mobile) throws IOException {
        String params = createSession();
        HttpPost req = new HttpPost("https://epass.icbc.com.cn/servlet/AsynGetDataServlet");
        HttpEntity entity = new StringEntity(params + "&loginID=" + mobile, ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
        req.setEntity(entity);
        String res = client.execute(req, responseHandler);
        JsonObject jsonObject = gson.fromJson(res, JsonObject.class);
        if (jsonObject.get("SendMsgTraceNum") != null) {
            return true;
        } else {
            System.err.println(jsonObject.get("ErrorMsg").getAsString());
            return false;
        }
    }

    @Override
    public boolean send(String mobile) {
        try {
            return asynGetDataServlet(mobile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
