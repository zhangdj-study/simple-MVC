package com.qyhy.mvc;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhangdj
 * @date 2021/03/08
 * 用于执行对应的方法
 */
public class MvcBean {

    private String url;

    /**
     * bean的名称
     */
    private String targetName;

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 目标方法
     */
    private Method targetMethod;

    ApplicationContext applicationContext;

    /**
     * 懒加载
     * @param args
     * @return
     */
    public Object run(Object... args) throws InvocationTargetException, IllegalAccessException {
        if (null == target) {
            target = applicationContext.getBean(targetName);
        }
        return targetMethod.invoke(target, args);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
