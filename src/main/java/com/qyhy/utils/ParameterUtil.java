package com.qyhy.utils;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangdj
 * @date 2021/03/08
 */
public class ParameterUtil {

    /**
     * 获取方法的所有变量名称
     * @param method 方法
     * @return 变量名称
     */
    public static List<String> getParameterName(Method method) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] params = u.getParameterNames(method);
        if (params.length <= 0) {
            return new ArrayList<>();
        }
        return Arrays.asList(params);
    }
}
