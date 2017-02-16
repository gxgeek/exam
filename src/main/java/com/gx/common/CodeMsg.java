package com.gx.common;

/**
 * Created by gx on 2017/2/2.
 */
public enum CodeMsg {
    //----------------------- 系统级状态码
    SUCCESS(200,"成功"),
    FAIL500(500,"系统出错"),
    //登录API
    FAIL1000(200,"登录成功"),
    FAIL1001(401,"登陆失败，用户名或密码错误"),

    //删除API
    FAIL2000(200,"删除成功"),

    //购买API
    FAIL3000(200,"购买成功"),
    FAIL3001(301,"库存不足"),
    FAIL3002(302,"余额不足"),
    //上传文件
    FAIL4001(4004,"文件过大");

    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
