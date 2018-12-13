package com.github.zengchao1212.sms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public class BeanFactory {
    private static final CloseableHttpClient httpClient;
    private static final Gson gson;

    static {
        httpClient= HttpClientBuilder.create()
                .addInterceptorLast((HttpRequestInterceptor) (httpRequest, httpContext) -> {
                    HttpHost host= (HttpHost) httpContext.getAttribute("http.target_host");
                    if(!httpRequest.containsHeader("Origin")){
                        httpRequest.addHeader("Origin",host.toString());
                    }
                    if(!httpRequest.containsHeader(HttpHeaders.REFERER)){
                        httpRequest.addHeader(HttpHeaders.REFERER,host.toString());
                    }
                    if(!httpRequest.containsHeader("Content-Type-X")){
                        httpRequest.addHeader(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlencoded; charset=UTF-8");
                    }else {
                        httpRequest.addHeader(HttpHeaders.CONTENT_TYPE,httpRequest.getFirstHeader("Content-Type-X").getValue());
                        httpRequest.removeHeaders("Content-Type-X");
                    }
                    System.out.println("-----------request header--------------");
                    System.out.println(host.toString()+httpRequest.getRequestLine().getUri());
                    for(Header header:httpRequest.getAllHeaders()){
                        System.out.println(header.toString());
                    }
                    System.out.println("-----------request header--------------");
                }).addInterceptorLast((HttpResponseInterceptor) (httpResponse, httpContext) -> {
//                    System.out.println("-----------response header--------------");
//                    for(Header header:httpResponse.getAllHeaders()){
//                        System.out.println(header.toString());
//                    }
//                    System.out.println("-----------response header--------------");
                })
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        gson=new GsonBuilder().create();
    }

    public static CloseableHttpClient getHttpClient(){
        return httpClient;
    }
    public static Gson getGson(){
        return gson;
    }
}
