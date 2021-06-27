package com.r00t.hyproxy.scanner;

import com.r00t.hyproxy.anno.EnableHyProxies;
import com.r00t.hyproxy.anno.ProxyService;
import com.r00t.hyproxy.proxy.DynamicInvocationHandler;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

public class ProxyScanner implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableHyProxies.class
                                                 .getCanonicalName());
        if (attributes == null)
            return;

        String[] basePackages = (String[]) attributes.get("value");
        if (basePackages.length == 0)
            basePackages = new String[]{(((StandardAnnotationMetadata)
                    importingClassMetadata).getIntrospectedClass()
                                           .getPackage()
                                           .getName())
            };

        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false, environment) {
                    @Override
                    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                        AnnotationMetadata metadata = beanDefinition.getMetadata();
                        return metadata.isIndependent() && metadata.isInterface();
                    }
                };
        provider.addIncludeFilter(new AnnotationTypeFilter(ProxyService.class));

        for (String basePackage : basePackages)
            for (BeanDefinition beanDefinition : provider.findCandidateComponents(basePackage))
                registry.registerBeanDefinition(
                        generateName(beanDefinition.getBeanClassName()),
                        Objects.requireNonNull(
                                getProxyBeanDefinition(beanDefinition.getBeanClassName())
                        )
                );
    }

    private String generateName(String beanClassName) {
        beanClassName = beanClassName.substring(
                beanClassName.lastIndexOf(".") + 1);
        beanClassName = beanClassName.replaceFirst(
                ".", String.valueOf(
                        beanClassName.charAt(0))
                           .toLowerCase());
        return beanClassName;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private BeanDefinition getProxyBeanDefinition(String beanClassName) {
        try {
            Class clazz = Class.forName(beanClassName);
            ProxyService proxyService = (ProxyService) clazz.getDeclaredAnnotation(ProxyService.class);
            DynamicInvocationHandler handler = new DynamicInvocationHandler(proxyService.handler());
            return BeanDefinitionBuilder.genericBeanDefinition(
                    clazz, () -> Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler))
                                        .getBeanDefinition();
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
