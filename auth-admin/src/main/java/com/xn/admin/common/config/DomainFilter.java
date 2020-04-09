package com.xn.admin.common.config;

import com.xn.common.utils.Constant;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p><b>通过CORS跨域资源共享来解决前后端分离中跨域请求问题</b></p><br>
 * CORS分简单请求和预检请求两类，当浏览器的请求方式是HEAD、GET或者POST，并且HTTP的头信息中不会超出以下字段：<br>
 * &nbsp;&nbsp;1.Accept<br>
 * &nbsp;&nbsp;2.Accept-Language<br>
 * &nbsp;&nbsp;3.Content-Language<br>
 * &nbsp;&nbsp;4.Last-Event-ID<br>
 * &nbsp;&nbsp;5.Content-Type：只限于三个值application/x-www-form-urlencoded、multipart/form-data、text/plain<br>
 * 时，浏览器会将该请求定义为简单请求，否则就是预检请求。预检请求会在正式通信之前，增加一次HTTP查询请求。浏览器先询问服务器，
 * 当前网页所在的域名是否在服务器的许可名单之中，以及可以使用哪些HTTP动词和头信息字段。只有得到肯定答复，浏览器才会发出正式
 * 的XMLHttpRequest请求，否则就报错。预检请求的方法是OPTIONS，表示这个请求是用来询问的，这个请求的头信息中的关键字段是Origin，
 * 表示请求来自哪个源。除了Origin字段，头信息中还包括两个字段：<br>
 * &nbsp;&nbsp;1.Access-Control-Request-Method:该字段是必须的，用来列出浏览器的CORS请求会用到哪些HTTP方法。<br>
 * &nbsp;&nbsp;2.Access-Control-Request-Headers:该字段是一个逗号分隔的字符串，指定浏览器CORS请求会额外发送的头信息字段。<br>
 */
public class DomainFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");//列出允许浏览器CORS请求的方法
        response.setHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Headers", String.format("%s, %s","Origin, X-Requested-With, Content-Type, Accept", Constant.AUTHORIZATION));//自己定义请求头字段
        HttpServletRequest request = (HttpServletRequest)req;
        if(request.getMethod().equals("OPTIONS"))//CORS预检查请求时，返回状态OK
            response.setStatus(HttpServletResponse.SC_OK);
        else
            chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
