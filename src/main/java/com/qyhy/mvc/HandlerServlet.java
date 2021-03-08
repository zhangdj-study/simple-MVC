package com.qyhy.mvc;

import com.qyhy.utils.ParameterUtil;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhangdj
 * @date 2021/01/11
 */
public class HandlerServlet extends HttpServlet {

    private WebApplicationContext context;

    private MvcBeanFactory mvcBeanFactory;

    @Override
    public void init() {
        System.out.println("init............");
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        mvcBeanFactory = new MvcBeanFactory(context);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doHandler(req, resp);
    }

    /**
     * todo 处理静态文件
     * @param req
     * @param resp
     */
    private void doHandler(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getServletPath();
        // 获取mvcBean
        MvcBean mvcBean = mvcBeanFactory.getMvcBean(uri);
        Assert.notNull(mvcBean, "not found mapping");
        // 参数封装（构建参数）
        Object[] params = buildParams(mvcBean, req, resp);
        try {
            // 调用方法
            Object result = mvcBean.run(params);
            processResult(result, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void processResult(Object result, HttpServletResponse response) throws IOException {
        // todo 处理结果
        PrintWriter writer = response.getWriter();
        writer.println("123");
        writer.println(result);
    }

    private Object[] buildParams(MvcBean mvcBean, HttpServletRequest request, HttpServletResponse response) {
        Method method = mvcBean.getTargetMethod();
        List<String> parameterName = ParameterUtil.getParameterName(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            }
            if (parameterTypes[i].isAssignableFrom(HttpServletResponse.class)) {
                args[i] = response;
            }
            if (request.getParameter(parameterName.get(i)) == null) {
                args[i] = null;
            } else {
                args[i] = convert(request.getParameter(parameterName.get(i)), parameterTypes[i]);
            }

        }
        return args;
    }
    public <T> T convert(String value, Class<T> targetClass) {
        if (value == null) {
            return null;
        }
        Object result = null;
        if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(value);
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(value);
        } else if (String.class.equals(targetClass)) {
            result = value;
        } else {
            System.out.println("不支持的类型");
        }

        return (T) result;
    }



    @Override
    public void destroy() {
        super.destroy();
    }

}
