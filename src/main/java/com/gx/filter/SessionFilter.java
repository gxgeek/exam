package com.gx.filter;


import com.gx.common.SysContext;
import com.gx.common.UUIDMap;
import com.gx.common.util.CookieTool;
import com.gx.model.Person;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by gx on 2017/2/4.
 */
public class SessionFilter  extends OncePerRequestFilter{



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String[] notFilter = new String[]{".jpeg", ".js" ,".css" ,"/login" ,"/logout","/show","/404","/permission"};
        String uri = request.getRequestURI();
        boolean doFilter = true;
        for (String s : notFilter) {
            if (uri.indexOf(s) != -1) {
                // 如果uri中包含不过滤的uri，则不进行过滤
                doFilter = false;
                break;
            }
        }
        //判断是否是首页请求，是首页不过滤
        if ("/".equals(request.getRequestURI())) {
            doFilter = false;
        }
        if (doFilter) {
            // 执行过滤
            // 从session中获取登录者实体
            Person obj =(Person) request.getSession().getAttribute("user");
            if (null == obj) {
                // 如果session中不存在登录者实体，则弹出框提示重新登录
                // 设置request和response的字符集，防止乱码
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                String loginPage = request.getContextPath()+"/";
                StringBuilder builder = new StringBuilder();
                builder.append("<script type=\"text/javascript\">");
                builder.append("alert('网页过期，请重新登录！');");
                builder.append("window.top.location.href='");
                builder.append(loginPage);
                builder.append("';");
                builder.append("</script>");
                out.print(builder.toString());
            } else {
                //防止两个客户端同时登陆
                String UUID="";
                try {
                    UUIDMap  uuidMap= UUIDMap.getInstance();
                    UUID = uuidMap.get(obj.getId().toString()).toString();
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                }
                String uid = CookieTool.getValue(request,obj.getId().toString());

                if (UUID.equals(uid)){
                    // 如果session中存在登录者实体，则继续
                    SysContext.setRequest((HttpServletRequest)request);
                    SysContext.setResponse((HttpServletResponse)response);
                    filterChain.doFilter(request, response);
                }else {
                    repeatLogin(request, response);
                }
            }
        } else {
            // 如果不执行过滤，则继续
            if ((request.getRequestURI().contains("/show"))) {
                Person obj = (Person)request.getSession().getAttribute("user");
                if (null != obj) {
                    //防止两个客户端同时登陆
                    String UUID="";
                    try {
                        UUIDMap  uuidMap= UUIDMap.getInstance();
                        UUID = uuidMap.get(obj.getId().toString()).toString();
                    }catch (Exception e){
                        logger.error(e.getMessage(),e);
                    }
                    String uid = CookieTool.getValue(request,obj.getId().toString());
                    if (!UUID.equals(uid)){
                        repeatLogin(request, response);
                    }
                }
            }


            filterChain.doFilter(request, response);
        }

    }

    /**
     * 当过期时输出的js
     * @param request
     * @param response
     * @throws IOException
     */
    private void repeatLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookieTool.rmoveCookie(request,response,"UUID");
        CookieTool.rmoveCookie(request,response,"JSESSIONID");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String loginPage = request.getContextPath()+"/login";
        StringBuilder builder = new StringBuilder();
        builder.append("<script type=\"text/javascript\">");
        builder.append("alert('其他客户端登录,请重新登录！');");
        builder.append("window.top.location.href='");
        builder.append(loginPage);
        builder.append("';");
        builder.append("</script>");
        out.print(builder.toString());
    }
}
