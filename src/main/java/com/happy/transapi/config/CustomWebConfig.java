package com.happy.transapi.config;

import com.happy.transapi.requests.General.RequestMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
@EnableWebMvc
public class CustomWebConfig extends WebMvcConfigurationSupport {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }

    @Bean
    @RequestScope
    public RequestMeta getRequestMeta(){
        return new RequestMeta();
    }
    @Bean
    public MappedInterceptor jwtTokenInterceptor()
    {
        return new MappedInterceptor(null, new JwtInterceptor(getRequestMeta()));
    }
}