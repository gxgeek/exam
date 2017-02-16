package com.gx.service;

import com.gx.dao.ContentMapper;
import com.gx.dao.TrxMapper;
import com.gx.model.Content;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/3.
 */
@Service
public class BaseService {

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private TrxMapper trxMapper;


    /**
     * 首页 商品
     * @param map
     * @param id
     * @param type
     * @throws Exception
     */
    public void index(ModelMap map, Integer id, String type) throws Exception{
        List<Map<String,Object>> list = contentMapper.selectProduct(id,type);
        map.addAttribute("productList",list);
    }

    /**
     * 编辑页提交
     * @param title
     * @param summary
     * @param image
     * @param detail
     * @param price
     * @return
     * @throws Exception
     */
    public Content publicSubmit(String title, String summary, String image, String detail, String price) throws  Exception{
        Content content = new Content();
        content.setTitle(title);
        content.setSummary(summary);
        content.setPrice(Long.valueOf(price));
        content.setImage(image);
        content.setText(detail);
        contentMapper.insertSelective(content);
        return content;
    }

    /**
     * 单个商品展示页
     * @param id
     * @param uid
     * @return
     * @throws Exception
     */
    public Object show(String id,Integer uid) throws  Exception {
        Map<String,Object> map = contentMapper.selectProductByUid(Long.valueOf(id),uid);
        return map;
    }

    /**
     * 编辑提交
     * @param id
     * @param title
     * @param summary
     * @param image
     * @param detail
     * @param price
     * @return
     * @throws Exception
     */
    public Content editSubmit(Integer id, String title, String summary, String image, String detail, String price)throws  Exception {
        Content content = new Content();
        content.setId(id);
        content.setTitle(title);
        content.setSummary(summary);
        content.setPrice(Long.valueOf(price));
        content.setImage(image);
        content.setText(detail);
        contentMapper.updateSelectiveById(content);
        return content;
    }

    /**
     * 跳转修改页
     * @param id
     * @return
     * @throws Exception
     */
    public Content edit(String id) throws  Exception{
        Content content = contentMapper.selectById(Long.valueOf(id));
        return content;
    }

    /**
     * 购买记录
     * @param uid
     * @return
     * @throws Exception
     */
    public Object account(Integer uid)throws  Exception {
        List<Map<String,Object>> list  = trxMapper.selectAccountByUid(uid);
        return list;
    }
}
