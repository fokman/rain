package com.rain.web.config;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageUtils {

    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, Locale.getDefault());
        } catch (Exception e) {
            return msgKey;
        }
    }
}
