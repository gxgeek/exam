package com.gx.common.aspect;

import com.gx.common.SysContext;
import com.gx.model.Person;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gx on 2016/12/13.
 */
@Component
@Aspect
public class BasicAspect {
    Logger logger = Logger.getLogger(BasicAspect.class);
    @Pointcut("execution(* com.gx.controller.*.*(..))")
    private  void arithmetic(){}
    @Pointcut("execution(* com.gx.controller.BuyerController.*(..))")
    private  void buyer(){}

    @Pointcut("execution(* com.gx.controller.ApiController.buy(..))")
    private  void buyerOne(){}

    @Pointcut("execution(* com.gx.controller.SellerController.*(..))")
    private  void seller(){}
    @Pointcut("execution(* com.gx.controller.ApiController.delete(..))")
    private  void delete(){}
    @Pointcut("execution(* com.gx.controller.ApiController.upload(..))")
    private  void upload(){}


    /**
     * 主要用于日志记录
     * @param proceedingJoinPoint
     * @return
     */
    @Around("arithmetic()")
    public Object aroundDeleteArticle(ProceedingJoinPoint proceedingJoinPoint){
        //开始计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //方法参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder info = new StringBuilder();
        info.append("---请求参数----{");
        for (Object arg : args){
            info.append(arg + " , ");
        }
        info.append("}");
        Object retValue = null;
        try {
            //执行方法
            retValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
        }finally {
            //最终打印日志
            StringBuilder sb_info = new StringBuilder();
            sb_info.append("该请求执行耗费：")
                    .append(stopWatch.getTime()+"ms---")
                    .append("方法名:------")
                    .append(proceedingJoinPoint.getTarget().getClass().getName()+".")
                    .append(proceedingJoinPoint.getSignature().getName())
                    .append(info).append(" ----------");
            logger.info(sb_info);
            stopWatch.reset();
        }
        return retValue;
    }


    /**
     * 权限校验 校验是否是用户的请求
     * @throws Throwable
     */
    @Before("buyer() || buyerOne()")
    public void buyerPrivilegeCheck()throws Throwable {
//        HttpSession session = SysContext.getSession();
        HttpServletRequest request = SysContext.getRequest();
        HttpServletResponse response = SysContext.getResponse();
        String userType = "";
        try {
            Person person = (Person) request.getSession().getAttribute("user");
            if (!person.getUserType().toString().equals("0")){
                throw new Exception("no privilege");
            }
        } catch (Throwable ex) {
            try {
                request.getRequestDispatcher("/permission").forward(request, response);
            } catch (Exception e) {
                logger.error("权限管理出现异常",e);
            }
        }
    }


    /**
     * 校验权限是否是商户的权限
     * @throws Throwable
     */
    @Before("seller() || delete()|| upload()")
    public void sellerPrivilegeCheck()throws Throwable {
        HttpSession session = SysContext.getSession();
        HttpServletRequest request = SysContext.getRequest();
        HttpServletResponse response = SysContext.getResponse();
        String userType = "";
        try {
            Person person = (Person) session.getAttribute("user");
            if (!person.getUserType().toString().equals("1")){
                throw new Exception("no privilege");
            }
        } catch (Exception ex) {
//            request.setAttribute("msg", "{\"res\":\"" + "无权限" + "\"}");
            try {
                request.getRequestDispatcher("/permission").forward(request, response);
            } catch (Exception e) {
                logger.error("权限管理出现异常",e);
            }
        }
    }


}

