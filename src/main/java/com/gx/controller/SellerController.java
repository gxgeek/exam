package com.gx.controller;

import com.gx.model.Content;
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
 * Created by gx on 2017/2/4.
 */
@Controller
public class SellerController {
    private Logger log = Logger.getLogger(BuyerController.class);
    @Autowired
    BaseService baseService;

    /**
     * 编辑页
     * @return
     */
    @RequestMapping("/public")
    public  String pub(HttpServletRequest request){
        return "public";
    }

    /**
     * 编辑页提交
     * @param title
     * @param summary
     * @param image
     * @param detail
     * @param price
     * @param map
     * @return
     */
    @RequestMapping("/publicSubmit")
    public  String publicSubmit(String title,String summary,
                                String image ,String detail ,
                                String price,ModelMap map,HttpServletRequest request){
        try {
            Content content = baseService.publicSubmit(title,summary,image,detail,price);
            map.addAttribute("product",content);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "publicSubmit";
    }

    /**
     *   跳转修改页
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/edit")
    public  String edit(String id,ModelMap map,HttpServletRequest request){
        try {
            Content content =baseService.edit(id);
            map.addAttribute("product",content);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "edit";
    }

    /**
     * 修改页提交
     * @param title
     * @param summary
     * @param image
     * @param detail
     * @param price
     * @param map
     * @param id
     * @return
     */
    @RequestMapping("/editSubmit")
    public  String editSubmit(String title,String summary,
                              String image ,String detail ,HttpServletRequest request,
                              String price,ModelMap map,@Param("id") Integer id){
        try {
            Content content = baseService.editSubmit(id,title,summary,image,detail,price);
            map.addAttribute("product",content);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "editSubmit";
    }

}
