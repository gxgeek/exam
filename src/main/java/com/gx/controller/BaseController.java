package com.gx.controller;

import com.gx.model.Person;
import com.gx.service.BaseService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gx on 2017/2/2.
 */
@Controller
public class BaseController {
    private Logger log  =Logger.getLogger(BaseController.class);
    @Autowired
    private BaseService baseService;

    /**
     * 首页
     * @param type
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/")
    public  String index(@Param("type") String type, ModelMap map, HttpServletRequest request){
        try {
            Person person = (Person) request.getSession().getAttribute("user");
            Integer id = null;
            if (person!=null){
                id = person.getId();
            }
            baseService.index(map,id,type);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "index";
    }

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping("/login")
    public  String login(){
        return "login";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public  String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }

    /**
     * 商品详情
     * @param id
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/show")
    public  String show(String id,ModelMap map,HttpServletRequest request){
        try {
            Person person = (Person) request.getSession().getAttribute("user");
            Integer uid = null;
            if (person!=null){
                uid = person.getId();
            }
            Object content =baseService.show(id,uid);
            map.addAttribute("product",content);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "show";
    }

    /**
     * 权限不足跳转页
     * @return
     */
    @RequestMapping("/permission")
    public  String permission(){
        return "permission";
    }

    /**
     * 404跳转页
     * @return
     */
    @RequestMapping("/404")
    public  String error(){
        return "404";
    }


}
