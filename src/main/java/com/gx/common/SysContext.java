package com.gx.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by gx on 2017/2/7.
 */
public class SysContext {
    private static ThreadLocal<HttpServletRequest> requestLocal=new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseLocal=new ThreadLocal<HttpServletResponse>();

    //在过滤器中将 request response 加入ThreadLocal（线程域中）
    public static HttpServletRequest getRequest(){
        return requestLocal.get();
    }

    public static void setRequest(HttpServletRequest request){
        requestLocal.set(request);
    }

    public static HttpServletResponse getResponse(){
        return responseLocal.get();
    }

    public static void setResponse(HttpServletResponse response){
        responseLocal.set(response);
    }

    public static HttpSession getSession(){
        return (HttpSession)(getRequest()).getSession();
    }

}
