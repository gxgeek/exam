package com.gx.thread;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by gx on 2016/12/23.
 */


//@Component
//@Scope("prototype")
//public class PrintTask implements  Runnable {
//    private  String name;
//
//    public PrintTask(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    @Override
//    public synchronized void run() {
//        try {
//            System.out.println(name + "任务开始执行");
//            for (int i = 0; i <= 100; i++) {
//                Thread.sleep(50);
//                System.out.println(name+"任务进行中%" + i  + "%");
//
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//}
