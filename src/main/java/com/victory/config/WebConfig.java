package com.victory.config;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.victory.common.web.converter.CustomObjectMapper;
import com.victory.common.web.converter.JsonTimeDeserializer;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ajkx
 * Date: 2017/8/17.
 * Time:9:19
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    /**
     * 拦截器级别的跨域设置，比shiroFilter后
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","PUT","DELETE","OPTIONS")
                .allowedHeaders("Content-Type","accept","Authorization","token","Access-Control-Allow-Headers","Origin");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new CustomObjectMapper();
    }

    /**
     * filter级别的跨域设置，优先级最大,比shiroFilter先
     * @return
     */
    @Bean
    public FilterRegistrationBean corsRegistration() {
        MyCorsRegistration corsRegistration = new MyCorsRegistration("/**");
        corsRegistration.allowedOrigins(CorsConfiguration.ALL)
                .allowedHeaders("Content-Type","accept","Authorization","token","Access-Control-Allow-Headers","Origin")
                .allowedMethods("POST","GET","PUT","DELETE","OPTIONS")
                .maxAge(3600);
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsRegistration.getCorsConfiguration());
        CorsFilter corsFilter = new CorsFilter(configurationSource);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter);
        registration.setEnabled(true);
        //数值越低，优先级越高
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
        registration.setFilter(filter);
        registration.setOrder(2);
        registration.addUrlPatterns("/*");
        registration.setEnabled(true);
        return registration;

    }
    //    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(exceptionHandlerExceptionResolver());
//        exceptionResolvers.add(restHandlerExceptionResolver());
//    }
//
//    @Bean
//    public RestHandlerExceptionResolver restHandlerExceptionResolver() {
//        return RestHandlerExceptionResolver.builder()
//                .messageSource(httpErrorMessageSource())
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .addErrorMessageHandler(EmptyResultDataAccessException.class, HttpStatus.NOT_FOUND)
//                .build();
//    }
//
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
//
//    @Bean
//    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
//        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
//        resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
//        return resolver;
//    }
    /**
     * 配置国际化相关参数
     * @return
     */
//    @Bean
//    public ResourceBundleMessageSource resourceBundleMessageSource() {
//        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
//        resourceBundleMessageSource.setBasename("messages/message");
//        return resourceBundleMessageSource;
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        return localeChangeInterceptor;
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//        super.addInterceptors(registry);
//    }
//
//    @Bean
//    public AcceptHeaderLocaleResolver acceptHeaderLocaleResolver() {
//        return new AcceptHeaderLocaleResolver();
//    }


}
