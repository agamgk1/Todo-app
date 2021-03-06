package io.github.agamgk1.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

//webMvcConfigurer pozwala wpiąc się do springa i pracuwac z roznymi rzeczmai np mozna uruchomic interceptory
@Configuration
class MvcConfiguration implements WebMvcConfigurer {
    private Set<HandlerInterceptor> interceptors;

    MvcConfiguration(Set<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    //zeny interceptor dzialal nalezy go "dodać" do springa. Dzieki takiemu sposobowi dodania automatycznie doda sie kazy interceptor jki mamy w aplikacji. Technika ta moze dotyczyc wszystkich interfejsow
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }
}
