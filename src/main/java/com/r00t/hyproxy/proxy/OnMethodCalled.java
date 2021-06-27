package com.r00t.hyproxy.proxy;

import java.lang.reflect.Method;
import java.util.List;

public interface OnMethodCalled {

    Object onMethodCalled(Method method, List<MethodParameter> parameters);
}
