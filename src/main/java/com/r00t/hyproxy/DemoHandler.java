package com.r00t.hyproxy;

import com.r00t.hyproxy.handler.AbstractProxyServiceHandler;
import com.r00t.hyproxy.proxy.MethodParameter;

import java.lang.reflect.Method;
import java.util.List;

public class DemoHandler extends AbstractProxyServiceHandler {

    @Override
    public Object onMethodCalled(Method method, List<MethodParameter> parameters) {
        System.out.println(method.getName());
        parameters.forEach(parameter -> System.out.println(parameter.getName()));

        System.out.println("\n");
        return null;
    }
}
