package com.andradscorporation.dscatalog.config;

import com.andradscorporation.dscatalog.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YAML = MediaType.valueOf("application/x-yaml");

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        Via QUERY PARAM
//        configurer.favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true).useRegisteredExtensionsOnly(false).defaultContentType(MediaType.ALL.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON);

//      Via HEADER PARAM
        configurer.favorParameter(false).parameterName("mediaType").ignoreAcceptHeader(false).useRegisteredExtensionsOnly(false).defaultContentType(MediaType.ALL.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON).mediaType("json", MediaType.APPLICATION_JSON).mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }
}
