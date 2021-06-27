package com.r00t.hyproxy.anno;

import com.r00t.hyproxy.scanner.ProxyScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ProxyScanner.class)
public @interface EnableHyProxies {

    String[] value() default {};
}
