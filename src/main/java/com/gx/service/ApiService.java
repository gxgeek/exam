package com.gx.service;

import com.gx.common.CodeMsg;
import com.gx.common.Reverse;
import com.gx.common.ReverseTwo;
import com.gx.common.util.http.HttpClientUtil;
import com.gx.dao.ContentMapper;
import com.gx.dao.PersonMapper;
import com.gx.dao.TrxMapper;
import com.gx.model.BuyPO;
import com.gx.model.Content;
import com.gx.model.Person;
import com.gx.model.Trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by gx on 2017/2/3.
 */
@Service
public class ApiService {

    @Autowired
    PersonMapper personMapper;
    @Autowired
    ContentMapper contentMapper;
    @Autowired
    TrxMapper trxMapper;

    /**
     * 验证登录接口
     * @param reverse
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public Person login(Reverse reverse, String userName, String password) throws  Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userName",userName);
        map.put("password",password);
        reverse.setCodeMsg(CodeMsg.FAIL1000);
        List<Person> list = personMapper.selectByMap(map);
        if (list.isEmpty()){
            reverse.setCodeMsg(CodeMsg.FAIL1001);
            return null;
        }else {
            Person  person = list.get(0);
            return person;
        }

    }

    /**
     * 删除商品
     * @param reverse
     * @param id
     * @throws Exception
     */
    public void delete(Reverse reverse, String id) throws Exception {
        contentMapper.deleteById(Long.valueOf(id));
        reverse.setCodeMsg(CodeMsg.FAIL2000);
    }

    /**
     * 购买接口
     * @param reverse
     * @param uid
     * @param list
     * @throws Exception
     */
    public void buy(Reverse reverse, Integer uid, List<BuyPO> list) throws Exception {
        for (BuyPO po:list){
            if (po.getNumber()<=0){
                continue;
            }
            Content content = contentMapper.selectById(Long.valueOf(po.getId()));
            Trx trx = new Trx();
            trx.setPersonId(uid);
            trx.setContentId(po.getId());
            trx.setBuyNum(po.getNumber());
            trx.setTime(System.currentTimeMillis());
            trx.setPrice((content.getPrice().intValue()));
            trxMapper.insertSelective(trx);
        }
        reverse.setCodeMsg(CodeMsg.FAIL3000);
    }

    /**
     * 上传接口，这里请求了一个自己写的图片服务器，将图片放到图片服务器中
     * @param reverse
     * @param file
     * @throws Exception
     */
    public void upload(ReverseTwo reverse, MultipartFile file) throws Exception{
        String json = HttpClientUtil.httpPostRequestProxy("http://59.110.141.65:8080/exam/alibaba/upload",file);
        reverse.setResult(json);
    }
}
