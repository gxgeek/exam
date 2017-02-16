package com.gx.controller;

import com.gx.model.Person;
import com.gx.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gx on 2017/2/4.
 */

@Controller
public class BuyerController {
    private Logger log = Logger.getLogger(BuyerController.class);
    @Autowired
    private BaseService baseService;


    /**
     * 购买记录
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/account")
    public  String account(ModelMap map, HttpServletRequest request){

        try {
            Person person = (Person) request.getSession().getAttribute("user");
            Object object =   baseService.account(person.getId());
            map.addAttribute("buyList",object);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "account";
    }

    /**
     * 购物车页面
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/settleAccount")
    public  String settleAccount(ModelMap map, HttpServletRequest request){

        return "settleAccount";
    }
}
