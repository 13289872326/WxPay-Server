package com.xn.admin.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xn.admin.common.annotation.ApiAuth;
import com.xn.common.utils.R;
import com.xn.common.utils.RepCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ygx
 * @date 2018-7-21 15:55:20
 */
@Component
public class ApiAuthInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(ApiAuthInterceptor.class);
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiAuth annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(ApiAuth.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }
        boolean flag = false;
        //从header中获取token
        String token = request.getHeader("XN-Auth");
        if(!StringUtils.isBlank(token)){
            String cache;
            String key = "sessionid:" + token;
            try {
                cache = redisTemplate.opsForValue().get(key);
                if (cache != null) {
                    flag = true;
                }
            } catch (Exception e) {
            }
        }
        if (!flag) {
            returnJson(response,  R.error(RepCode.ERROR_1003));
            return false;
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, R res){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            writer = response.getWriter();
            writer.print(JSONObject.toJSONString(res));
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
