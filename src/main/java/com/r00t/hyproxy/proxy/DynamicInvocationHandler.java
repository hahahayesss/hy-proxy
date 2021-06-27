package com.r00t.hyproxy.proxy;

import com.r00t.hyproxy.handler.AbstractProxyServiceHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DynamicInvocationHandler implements InvocationHandler {
    private final AbstractProxyServiceHandler handler;

    public DynamicInvocationHandler(Class<? extends AbstractProxyServiceHandler> handlerClazz)
    throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.handler = handlerClazz.getDeclaredConstructor()
                                   .newInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<MethodParameter> parameters = new ArrayList<>();
        for (int x = 0; x < method.getParameters().length; x++)
            parameters.add(
                    new MethodParameter(
                            method.getParameters()[x].getName(),
                            method.getParameters()[x].getAnnotations(),
                            method.getParameters()[x].getParameterizedType(),
                            args[x]
                    ));
        return this.handler.onMethodCalled(method, parameters);
    }
}
