package com.jinchi.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jinchi.config.interceptor.LoginInterceptor;
import com.jinchi.constant.HttpConstant;

/**
 * @author yuxuanjiao
 * @date 2017年7月12日 上午9:37:43
 * @version 1.0
 * @description 继承和重写一些配置，添加静态资源filter类似。
 */

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    private List<String> excludePathes = Arrays.asList(HttpConstant.NOT_LOGIN_URL);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/backend/**", "/backend");
        for (String path : excludePathes) {
            registration.excludePathPatterns(path);
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/img/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // TODO Auto-generated method stub
        super.addViewControllers(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        SerializerFeature[] features = new SerializerFeature[] { SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse };
        fastJsonHttpMessageConverter.setFeatures(features);
        // StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        // converters.add(stringHttpMessageConverter);
        converters.add(fastJsonHttpMessageConverter);
    }
}
