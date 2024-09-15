package com.datn.maguirestore.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class Translator {
//
//    private static final Logger log = LoggerFactory.getLogger(Translator.class);
//    private static ReloadableResourceBundleMessageSource resourceBundleMessageSource;
//
//    @Autowired
//    Translator(ReloadableResourceBundleMessageSource resourceBundleMessageSource) {
//        this.resourceBundleMessageSource = resourceBundleMessageSource;
//    }
//
//    public static String toLocalWithDefault(String msgCode, String defaultMessage, @Nullable Object[] args) {
//        Locale locale = LocaleContextHolder.getLocale();
//        return resourceBundleMessageSource.getMessage(msgCode, args, defaultMessage, locale);
//    }
//
//    public static String toLocalWithDefault(String msgCode, String defaultMessage) {
//        Locale locale = LocaleContextHolder.getLocale();
//        return resourceBundleMessageSource.getMessage(msgCode, null, defaultMessage, locale);
//    }
//
//    public static String toLocal(String msgCode, @Nullable Object[] args) {
//        Locale locale = LocaleContextHolder.getLocale();
//        return resourceBundleMessageSource.getMessage(msgCode, args, locale);
//    }
//
//    public static String toLocalParam(String msgCode, @Nullable Object... args) {
//        Locale locale = LocaleContextHolder.getLocale();
//        return resourceBundleMessageSource.getMessage(msgCode, args, locale);
//    }
//
//    public static String toLocal(String msgCode, @Nullable Object[] args, String lang) {
//        Locale locale = Locale.KOREA;
//        if (StringUtils.equals(Locale.ENGLISH.getLanguage(), lang)) {
//            locale = Locale.ENGLISH;
//        }
//        return resourceBundleMessageSource.getMessage(msgCode, args, locale);
//    }
//
//    public static String toLocal(String msgCode) {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            source.setBasename("i18n/messages");
//            source.setDefaultEncoding("UTF-8"); // Set the default encoding to UTF-8
//            return source.getMessage(msgCode, null, locale);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            return "";
//        }
//    }
//
//    public static String toLocaleKo(String msgCode) {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            source.setBasename("i18n/messages_ko");
//            return source.getMessage(msgCode, null, locale);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            return "";
//        }
//    }
//
//    public static String toLocaleEn(String msgCode) {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            source.setBasename("i18n/messages_en");
//            return source.getMessage(msgCode, null, locale);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            return "";
//        }
//    }
//
//    public static String translate(String str, String language) {
//        if (language.equals("ko")) return toLocaleKo(str);
//        return toLocaleEn(str);
//    }
//
//    public static String getMessage(String msgCode, Object... args) {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            source.setBasename("i18n/messages");
//            source.setDefaultEncoding("UTF-8"); // Set the default encoding to UTF-8
//            String message = source.getMessage(msgCode, null, locale);
//            //            String message = resourceBundleMessageSource.getMessage(msgCode, null, locale);
//            return MessageFormat.format(message, args);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            return "";
//        }
//    }
//
//    public static String toLocaleVi(String msgCode) {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        try {
//            Locale locale = LocaleContextHolder.getLocale();
//            source.setDefaultEncoding("UTF-8"); // Set the default encoding to UTF-8
//            source.setBasename("i18n/messages_vi");
//            return source.getMessage(msgCode, null, locale);
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            return "";
//        }
//    }
}
