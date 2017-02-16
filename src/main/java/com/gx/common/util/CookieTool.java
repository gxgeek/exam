package com.gx.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by window on 2015/9/13.
 */
public class CookieTool {
    //读取Cookie
    public static String getValue(HttpServletRequest request, String cookieNme) {
        final Cookie[] Cookies = request.getCookies();
        if (Cookies != null) {
            for (final Cookie cookie : Cookies) {
                final String name = cookie.getName();
                if (cookieNme.equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    //保存Cookie
    public static void setCookie(HttpServletResponse response, String key, String val) {
        setCookie(response, key, val, "/", Integer.valueOf(3600*24*15));
    }
    public static void setCookie(HttpServletResponse response, String key, String val, String path, Integer times) {
        Cookie cookie = new Cookie(key, val);
        cookie.setPath(path);
        cookie.setMaxAge(times.intValue());
        response.addCookie(cookie);
    }
    public static String rmoveCookie(HttpServletRequest request, HttpServletResponse response,String cookieNme) {
        final Cookie[] Cookies = request.getCookies();
        if (Cookies != null) {
            for (final Cookie cookie : Cookies) {
                final String name = cookie.getName();
                if (cookieNme.equals(name)) {
                     cookie.setMaxAge(0);
                     response.addCookie(cookie);
                }
            }
        }
        return null;
    }

}
