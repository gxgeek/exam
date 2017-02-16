package com.gx.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by gx on 2017/2/2.
 */
public class ReverseTwo {
    private CodeMsg codeMsg = CodeMsg.SUCCESS;
    private Integer code = codeMsg.getCode();
    private String message = codeMsg.getMsg();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;


    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public Integer getCode() {
        return codeMsg.getCode();
    }

    public String getMessage() {
        return codeMsg.getMsg();
    }



    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
    }

    public static void main(String[] args) {
        ReverseTwo reverse = new ReverseTwo();
        reverse.setCodeMsg(CodeMsg.FAIL1001);
        reverse.toString();
        reverse.setResult("success");
        System.out.println(reverse.toString());
    }


}
