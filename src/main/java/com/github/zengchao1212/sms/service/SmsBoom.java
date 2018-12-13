package com.github.zengchao1212.sms.service;

import java.io.IOException;

/**
 * @author zengchao
 * @date 2018/12/12
 */
public interface SmsBoom {
    boolean send(String mobile) throws IOException;
}
