package com.qyhy.control;

import com.qyhy.mvc.MvcMapping;
import org.springframework.stereotype.Controller;

/**
 * @author zhangdj
 * @date 2021/03/08
 */
@Controller
public class MyController {

    @MvcMapping("/test")
    public String test(String param) {
        param = param + ":invoke";
        return param;
    }
}
