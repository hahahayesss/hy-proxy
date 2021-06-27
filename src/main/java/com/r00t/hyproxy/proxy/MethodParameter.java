package com.r00t.hyproxy.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class MethodParameter {
    private String name;
    private Annotation[] annotations;
    private Type type;
    private Object value;

    public MethodParameter(String name,
                           Annotation[] annotations,
                           Type type,
                           Object value) {
        this.name = name;
        this.annotations = annotations;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
