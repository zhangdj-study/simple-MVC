package com.qyhy.mvc;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangdj
 * @date 2021/03/08
 * 装载并保存、保存路径映射
 */
public class MvcBeanFactory {

    private WebApplicationContext webApplicationContext;

    private Map<String,MvcBean> apiMap = new HashMap<String, MvcBean>();


    public MvcBeanFactory(WebApplicationContext webApplicationContext) {
        Assert.notNull(webApplicationContext, "applicationContext is null");
        this.webApplicationContext = webApplicationContext;
        // 加载所有标记了@MvcMapping注解的方法映射到apiMap中
        loadApiFromSpringBeans();
    }

    private void loadApiFromSpringBeans() {
        String[] beanDefinitionNames = webApplicationContext.getBeanDefinitionNames();
        Class<?> type;
        for (String name : beanDefinitionNames) {
            type = webApplicationContext.getType(name);
            for (Method method : type.getDeclaredMethods()) {
                MvcMapping mvcMapping = method.getAnnotation(MvcMapping.class);
                if (null != mvcMapping) {
                    addApiItem(mvcMapping, name, method);
                }
            }
        }
    }

    private void addApiItem(MvcMapping mvcMapping, String beanName, Method method) {
        MvcBean mvcBean = new MvcBean();
        // 设置url
        mvcBean.setUrl(mvcMapping.value());
        mvcBean.setTargetMethod(method);
        mvcBean.setTargetName(beanName);
        mvcBean.setApplicationContext(this.webApplicationContext);
        apiMap.put(mvcMapping.value(), mvcBean);
    }

    public MvcBean getMvcBean(String uri) {
        return apiMap.get(uri);
    }
}
