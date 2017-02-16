package com.gx.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zq on 2016/11/30.
 */
public class DateUtil {
    /**
     *
     * <p class="detail">
     * 功能：获取时间HHmmss
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getTimeStr() {
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        String time = df.format(new Date());
        return (time);
    }

    /**
     * 获取年月string
     * @return
     */
    public static String getMonthStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String time = df.format(new Date());
        return (time);
    }

    /**
     *
     * <p class="detail">
     * 功能：获取日期yyyyMMdd
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(new Date());
        return (time);
    }

    /**
     *
     * @return
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(date);
        return (time);
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间日期yyyyMMddHHmmss
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getDateTimeStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        return (time);
    }

    /**
     * 返回精确到毫秒的格式化时间 20160828090501555
     * @return
     */
    public static String getDateTimeStrMilliSecond(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间日期yyyy-MM-dd HH:mm:ss
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getTime(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    /**
     * 获取时间日期yyyy-MM-dd
     * @return
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     *
     * <p class="detail">
     * 功能：获取date对象
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param time  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time==null || time.equals("")){
            return null;
        }
        try {
            return df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取date对象
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date yyyy-MM-dd
     * @return
     */
    public static Date getDate(String date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if(date==null || date.equals("")){
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，毫秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date
     * @return
     */
    public static Long getTimeLong(String date){
        Date d = getTime(date);
        return d.getTime();
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，毫秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date
     * @return
     */
    public static Long getTimeLong(Date date){
        return date.getTime();
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static Integer getTimeLong(){
        return (int) (System.currentTimeMillis()/1000);
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static String getWeekStart(){
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        String s = df.format(cal.getTime());
        return s+" 00:00:01";
    }

    /**
     * 获取本周最后一天
     * @return
     */
    public static String getWeekEnd(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);		//增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        String s = df.format(cal.getTime());
        return s+" 23:59:59";
    }

    /**
     * 判断两个字符串时间是否同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1,String date2){
        return date1.substring(0,10).equals(date2.substring(0,10));
    }

    public static void main(String[] args) throws Exception {
        // 	Long l = getTimeLong("2016-1-21 13:56:39");
        Integer l = getTimeLong();
        System.out.println(l);
        String s1 = "2016-01-21 13:56:39";
        String s2 = "2016-01-21 13:52:39";

        System.out.println(s1.substring(10));
        System.out.println(isSameDay(s1,s2));
    }
}
