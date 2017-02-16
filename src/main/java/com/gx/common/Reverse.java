package com.gx.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by gx on 2017/2/2.
 */
public class Reverse {
    private CodeMsg codeMsg = CodeMsg.SUCCESS;
    private Integer code = codeMsg.getCode();
    private String message = codeMsg.getMsg();
    private boolean result;


    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public Integer getCode() {
        return codeMsg.getCode();
    }

    public String getMessage() {
        return codeMsg.getMsg();
    }

    public boolean getResult() {
        if (codeMsg.getCode()==200) {
            result=true;
        }
        return result;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 测试代码
     * @param args
     */
    public static void main(String[] args) {
        Reverse reverse = new Reverse();
        reverse.setCodeMsg(CodeMsg.FAIL3000);
        System.out.println(reverse.toString());
    }


}
