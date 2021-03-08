package com.qyhy.mvc;

import java.lang.annotation.*;

/**
 * @author zhangdj
 * @date 2021/03/08
 * 用于映射url和方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MvcMapping {

    /**
     * 路径
     * @return 路径
     */
    String value();
}
