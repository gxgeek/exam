package com.gx.filter;

import com.gx.common.util.CookieTool;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gx on 2016/12/26.
 */
public class PermissionsFilter implements Filter {
    private Logger log = Logger.getLogger(PermissionsFilter.class);

//    @Autowired
//    private RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        ServletContext context = filterConfig.getServletContext();
//        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
//        redisService = (RedisService) ctx.getBean("redisServiceImpl");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
            HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
            HttpSession session = httpServletRequest.getSession();
            Integer uid = (Integer) session.getAttribute("uid");
            if (uid!=null){
                //单点登录验证
//                String cookiePass = CookieTool.getValue(httpServletRequest,uid.toString());
//                String pass=redisService.get("ssoCookie:"+uid);
//                if (!cookiePass.equals(pass)){
//                    httpServletRequest.getSession().setAttribute("msg","另一台设备已登录");
//                    httpServletResponse.sendRedirect((httpServletRequest.getContextPath()+"/wx/login?msg=另一台设备已登录"));
//                }
                chain.doFilter(servletRequest,servletResponse);
            }else {
                httpServletResponse.sendRedirect((httpServletRequest.getContextPath()+"/wx/login"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void destroy() {

    }
    //判断来源
    private String getEquipType (String userAgent) {
        String type = "other";
        if (userAgent.toLowerCase().contains("iphone")) type = "iphone";
        else if (userAgent.toLowerCase().contains("android")) type = "android";
        return type;
    }
    //获取指定Cookie值
    public  String getCookieOrAttr (HttpServletRequest request, String key) {
        String attrKeyVal = request.getAttribute(key) == null?null:request.getAttribute(key).toString();
        String keyVal = CookieTool.getValue(request, key);
        keyVal = !StringUtils.isEmpty(attrKeyVal)?attrKeyVal:keyVal;
        return keyVal;
    }


}
