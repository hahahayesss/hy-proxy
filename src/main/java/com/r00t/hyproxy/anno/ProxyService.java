package com.r00t.hyproxy.anno;

import com.r00t.hyproxy.handler.AbstractProxyServiceHandler;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ProxyService {

    Class<? extends AbstractProxyServiceHandler> handler();

    String[] args() default {};
}
