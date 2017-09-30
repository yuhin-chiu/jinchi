package com.jinchi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jiaoyuxuan on 2017/8/9.
 */
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileConfig {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
