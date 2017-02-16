package com.gx.controller;

import com.gx.common.CodeMsg;
import com.gx.common.Reverse;
import com.gx.common.ReverseTwo;
import com.gx.common.UUIDMap;
import com.gx.common.util.CookieTool;
import com.gx.common.util.DateUtil;
import com.gx.common.util.MD5Util;
import com.gx.model.BuyPO;
import com.gx.model.Person;
import com.gx.service.ApiService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

/**
 * Created by gx on 2017/2/2.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private Logger log = Logger.getLogger(ApiController.class);
    @Autowired
    ApiService apiService;


    /**
     * 验证登录接口
     * @param userName
     * @param password
     * @param request
     * @param map
     * @param response
     * @return
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(@RequestParam(value = "userName") String userName,
                        @RequestParam(value = "password") String password,
                        HttpServletRequest request, ModelMap map,
                        HttpServletResponse response,
                        HttpSession session
                         ){
        Reverse reverse = new Reverse();
        try {
            Person person = apiService.login(reverse,userName,password);
            CookieTool.setCookie(response,"JSESSIONID",session.getId());
            //防止多客户端登录

            request.getSession().setAttribute("user",person);
            String UUID = MD5Util.getMD5Str(DateUtil.getTime());
            UUIDMap  uuidMap= UUIDMap.getInstance();
            uuidMap.put(person.getId().toString(),UUID);
            CookieTool.setCookie(response,person.getId().toString(),UUID);
        }catch (Exception e){
            reverse.setCodeMsg(CodeMsg.FAIL500);
            log.error(e.getMessage(),e);

        }
        return reverse.toString();
    }


    /**
     * 删除商品接口
     * @param id
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete")
    public   String delete(@Param(value = "id") String id,
                                       @RequestParam(value = "password",required = false) String password,
                                       HttpServletRequest request,HttpServletResponse response){
        Reverse reverse = new Reverse();
        try {
            Person person = (Person) request.getSession().getAttribute("user");
            if (person.getUserType().toString().equals("0")){
                response.sendRedirect("/permission");
            }
            apiService.delete(reverse,id);
        }catch (Exception e){
            reverse.setCodeMsg(CodeMsg.FAIL500);
            log.error(e.getMessage(),e);
        }
        return reverse.toString();
    }


    /**
     * 购买购买商品接口
     * @param list
     * @param request
     * @return
     */
    @RequestMapping(value = "/buy")
    public String buy(@RequestBody List<BuyPO> list,
                      HttpServletRequest request){
        Reverse reverse = new Reverse();
        try {
            Person person = (Person)request.getSession().getAttribute("user");
            Integer uid = person.getId();
            apiService.buy(reverse,uid,list);
        }catch (Exception e){
            reverse.setCodeMsg(CodeMsg.FAIL500);
            log.error(e.getMessage(),e);
        }
        return reverse.toString();
    }



    @RequestMapping(value = "/upload")
    public   String upload(@RequestParam(value = "file",required = false) MultipartFile file,
                           HttpServletRequest request,HttpServletResponse response){
        ReverseTwo reverse = new ReverseTwo();
        FileChannel fc= null;
        try {
            InputStream  inputStream= file.getInputStream();
            if (inputStream.available()>1024*1024){
                reverse.setCodeMsg(CodeMsg.FAIL4001);
            }
            apiService.upload(reverse,file);
        }catch (Exception e){
            reverse.setCodeMsg(CodeMsg.FAIL500);
            log.error(e.getMessage(),e);
        }
        return reverse.toString();
    }

}
