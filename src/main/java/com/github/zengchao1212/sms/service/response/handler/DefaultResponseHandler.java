package com.github.zengchao1212.sms.service.response.handler;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zengchao
 * @date 2018/12/13
 */
public class DefaultResponseHandler implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        if(response.getStatusLine().getStatusCode()!=200){
            throw new ClientProtocolException(response.getStatusLine().toString());
        }

        String charset = "UTF-8";
        Header contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE);
        if (contentType != null) {
            for (HeaderElement element : contentType.getElements()) {
                NameValuePair resCharset = element.getParameterByName("charset");
                if (resCharset != null) {
                    charset = resCharset.getValue();
                    break;
                }
            }

        }
        try (InputStream in=response.getEntity().getContent()){
            return IOUtils.toString(in, charset);
        }
    }
}
