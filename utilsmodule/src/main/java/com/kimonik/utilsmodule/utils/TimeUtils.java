package com.kimonik.utilsmodule.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * * ==================================================
 * name:            TimeUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/22
 * description：   时间处理工具类
 * history：
 * * ==================================================
 */

public class TimeUtils {
    /**
     * @return 返回系统当前时间--时 分
     */
    public static String getCurentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * @return 返回系统当前时间--时 分
     */
    public static String getCurentTimeTotal() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault());
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 yyyy年MM月dd日
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在日期
     *
     * @return 返回短时间格式 yyyy-MM-dd
     */
    public static String getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate 日期
     * @return yyyy-MM-dd
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate 日期
     * @return 星期几
     */
    public static String getWeek(String sdate) {
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(c.getTime());
    }


    /**
     * 根据yyyy-MM-dd  获得日期是星期几
     *
     * @param sdate 日期
     * @return 星期几  整数
     */
    public static int getWeekInt(String sdate) {
        String str = getWeek(sdate);
        int day = -1;
        if ("星期日".equals(str)) {
            day = 1;
        } else if ("星期一".equals(str)) {
            day = 2;
        } else if ("星期二".equals(str)) {
            day = 3;
        } else if ("星期三".equals(str)) {
            day = 4;
        } else if ("星期四".equals(str)) {
            day = 5;
        } else if ("星期五".equals(str)) {
            day = 6;
        } else if ("星期六".equals(str)) {
            day = 7;
        }
        return day;
    }

    /**
     * 获取当前月份
     *
     * @return 月份字符串  01或11格式
     */
    public static String getCurrentMonthStr() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;// 获取当前月份
        if (month < 10) {
            return "0" + month;
        } else {
            return "" + month;

        }
    }

    /**
     * 将月份转化为两位数的字符串
     *
     * @return 月份字符串  01或11格式
     */
    public static String getCurrentMonthStr(int month) {
        if (month < 10) {
            return "0" + month;
        } else {
            return "" + month;

        }
    }


    /**
     * 获取当前月份
     *
     * @return 月份字符串
     */
    public static int getCurrentMonthInt() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;// 获取当前月份
    }

    /**
     * 获取当前月份
     *
     * @return 月份字符串
     */
    public static int getCurrentDayInt() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);// 获取当前月份
    }


    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);// 获取当前年份
    }

    /**
     * 获取当前月份的1号字符串
     */
    public static String getCurrentFirstOfTheMonteh() {
        return getCurrentYear() + "-" + getCurrentMonthStr() + "-01";
    }

    /**
     * 获取某年某月
     */
    public static String getCurrentFirstOfTheMonteh(int year, int month) {
        return year + "年" + month + "月";
    }

    /**
     * * 获取系统当前年月
     *
     * @return 类似2018年1月的字符串
     */
    public static String getCurrentYearMonth() {
        return getCurrentYear() + "年" + getCurrentMonthInt() + "月";
    }

    /**
     * 获取系统当前月的1号是星期几
     * 1----星期日
     * 7----星期六
     */
    public static int getCurrentDayOfWeekTheFirstOfMonteh() {
        return getWeekInt(getCurrentYear() + "-" + getCurrentMonthStr() + "-01");
    }

    /**
     * 获取当前月的1号是星期几
     * 1----星期日
     * 7----星期六
     */
    public static int getCurrentDayOfWeekTheFirstOfMonteh(int year, int month) {
        if (month < 10) {
            return getWeekInt(year + "-0" + month + "-01");
        }
        return getWeekInt(year + "-" + month + "-01");
    }

    /**
     * 获取某年某月有多少天
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前所在月份有多少天
     */
    public static int getCurrentDayOfMonth() {
        Calendar c = Calendar.getInstance();
        c.set(getCurrentYear(), getCurrentMonthInt(), 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前所在月份的上一个月份有多少天
     */
    public static int getLastDayOfMonth() {
        Calendar c = Calendar.getInstance();
        if (getCurrentMonthInt() == 1) {
            c.set(getCurrentYear() - 1, 12, 0); //输入类型为int类型
        } else {
            c.set(getCurrentYear(), getCurrentMonthInt() - 1, 0); //输入类型为int类型
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前所在月份的下一个月份有多少天
     */
    public static int getNextDayOfMonth() {
        Calendar c = Calendar.getInstance();
        if (getCurrentMonthInt() == 12) {
            c.set(getCurrentYear() + 1, 1, 0); //输入类型为int类型
        } else {
            c.set(getCurrentYear(), getCurrentMonthInt() + 1, 0); //输入类型为int类型
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取当前月的上一个月
     */
    public static int getLastMonth(int currentMonth) {
        if (currentMonth == 1) {
            return 12;
        } else {
            return currentMonth - 1;
        }
    }

    /**
     * 获取当前月的上一个月所在的年份
     */
    public static int getYearOfLastMonth(int currentYear, int currentMonth) {
        if (currentMonth == 1) {
            return currentYear - 1;
        } else {
            return currentYear;
        }
    }


    /**
     * 获取当前月的下一个月
     */
    public static int getNextMonth(int currentMonth) {
        if (currentMonth == 12) {
            return 1;
        } else {
            return currentMonth + 1;
        }
    }


    /**
     * 获取当前月的下一个月所在的年份
     */
    public static int getYearOfNextMonth(int currentYear, int currentMonth) {
        if (currentMonth == 12) {
            return currentYear + 1;
        } else {
            return currentYear;
        }
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1 yyyy-mm-dd型日期字符串
     * @param date2 yyyy-mm-dd型日期字符串
     * @return true--->第一个日期在第二个日期后面
     */
    public static boolean compareDate(String date1, String date2) {
        int one = StringUtils.string2Integer(date1.replace("-", ""));
        int two = StringUtils.string2Integer(date2.replace("-", ""));
        return one > two;
    }
    /**
     * 计算两个日期之间间隔几天,未考虑闰年
     */
    public static int differenceDaate(String date1, String date2){
        String[] da1=date1.split("-");
        String[] da2=date2.split("-");
        int[] da11={
                StringUtils.string2Integer(da1[0]),
                StringUtils.string2Integer(da1[1]),
                StringUtils.string2Integer(da1[2]),
        };
        int[] da22={
                StringUtils.string2Integer(da2[0]),
                StringUtils.string2Integer(da2[1]),
                StringUtils.string2Integer(da2[2]),
        };
        int[] mothNum={31,28,31,30,31,30,31,31,30,31,31};
        int sum1=0,sum2=0;
        for (int i = 0; i < da11[1] - 1; i++) {
            sum1+=mothNum[i];
        }
        for (int i = 0; i < da22[1] - 1; i++) {
            sum2+=mothNum[i];
        }
        sum1+=da11[2];
        sum2+=da22[2];
        int year=(StringUtils.string2Integer(da1[0])-StringUtils.string2Integer(da2[0]))*365;
        return sum2-sum1;
    }


}
