package com.gx.controller.extend;

import com.gx.common.Constant;
import com.gx.common.Reverse;
import com.gx.common.util.DateUtil;
import com.gx.common.util.FileUtil;
import com.gx.common.util.MD5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by gx on 2017/2/8.
 */

@RestController
public class AlibabaController {

    @RequestMapping(value = "/alibaba/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String picName = MD5Util.getMD5EncodeStr(DateUtil.getTime()) + ".jpeg";
        String picUrl = Constant.URL + picName;
//            String picPath = Constant.PATH+picName;
        try {
            FileUtil.uploadFile(file.getInputStream(), request.getSession().getServletContext().getRealPath("/pic") + "/" + picName);
        } catch (Exception e) {

        }
        return picUrl;
    }

}
