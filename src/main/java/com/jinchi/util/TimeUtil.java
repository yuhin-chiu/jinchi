package com.jinchi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuxuanjiao
 * @date 2017年7月13日 下午2:17:31
 * @version 1.0
 */

public class TimeUtil {

    private static final ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            Map<String, SimpleDateFormat> map = new HashMap<String, SimpleDateFormat>();
            map.put("FORMATER_TIME", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            map.put("FORMATER_DAY", new SimpleDateFormat("yyyy-MM-dd"));
            return map;
        }
    };

    private static SimpleDateFormat getTimeFormat() {
        return threadLocal.get().get("FORMATER_TIME");
    }

    private static SimpleDateFormat getDayFormat() {
        return threadLocal.get().get("FORMATER_DAY");
    }

    // 获取当前的时间秒数
    public static Long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 将时间秒数转成yyyy-MM-dd HH:mm:ss格式
     * 
     * @param seconds
     * @return timeStr
     */
    public static String formatTime(Long seconds) {
        return getTimeFormat().format(new Date(seconds * 1000));
    }

    /**
     * 将时间秒数转成yyyy-MM-dd
     * 
     * @param seconds
     * @return dayStr
     */
    public static String formatDay(Long seconds) {
        return getDayFormat().format(new Date(seconds * 1000));
    }

    /**
     * 将yyyy-MM-dd格式转成时间秒数
     * 
     * @param timeStr
     * @return timeSecond
     */
    public static Long parseTime(String timeStr) {
        try {
            return getTimeFormat().parse(timeStr).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式转成时间秒数
     * 
     * @param dayStr
     * @return daySecond
     */
    public static Long parseDay(String dayStr) {
        try {
            return getDayFormat().parse(dayStr).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 将"yyyy-MM-dd yyyy-MM-dd" 转成时间秒数
     * 
     * @param dayRange
     * @return [beginDaySecond, endDaySecond]
     */
    public synchronized static Long[] splitDayRangeSmall(String dayRange) {
        try {
            String beginTimeStr = dayRange.substring(0, 10);
            String endTimeStr = dayRange.substring(dayRange.length() - 10);
            Long beginTime = getDayFormat().parse(beginTimeStr).getTime() / 1000;
            Long endTime = getDayFormat().parse(endTimeStr).getTime() / 1000;
            return new Long[] { beginTime, endTime };
        } catch (Exception e) {
        }
        return new Long[] { -1L, -1L };
    }
    // 很多情况最后一天也要查
    public static Long[] splitDayRangeBig(String dayRange){
        try {
            String beginTimeStr = dayRange.substring(0, 10);
            String endTimeStr = dayRange.substring(dayRange.length() - 10);
            Long beginTime = getDayFormat().parse(beginTimeStr).getTime() / 1000;
            // 最后时间加一天
            Long endTime = getDayFormat().parse(endTimeStr).getTime() / 1000 + 3600 * 24;
            return new Long[] { beginTime, endTime };
        } catch (Exception e) {
        }
        return new Long[] { -1L, -1L };
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss yyyy-MM-dd HH:mm:ss" 转成时间秒数
     * 
     * @param timeRange
     * @return [beginTimeSecond, endTimeSecond]
     */
    public synchronized static Long[] splitTimeRange(String timeRange) {
        try {
            String beginTimeStr = timeRange.substring(0, 10);
            String endTimeStr = timeRange.substring(timeRange.length() - 19);
            Long beginTime = getTimeFormat().parse(beginTimeStr).getTime() / 1000;
            Long endTime = getTimeFormat().parse(endTimeStr).getTime() / 1000;
            return new Long[] { beginTime, endTime };
        } catch (Exception e) {
        }
        return new Long[] { -1L, -1L };
    }

    public static String[] splitDayRangeToString(String dayRange) {
        try {
            String beginTimeStr = dayRange.substring(0, 10);
            String endTimeStr = dayRange.substring(dayRange.length() - 10);
            return new String[] { beginTimeStr, endTimeStr };
        } catch (Exception e) {
        }
        return new String[] { "1970-01-01", "2099-12-31" };
    }

    // yyyy-MM-dd HH:mm:ss
    public static String[] splitTimeRangeToString(String timeRange) {
        try {
            String beginTimeStr = timeRange.substring(0, 19);
            String endTimeStr = timeRange.substring(timeRange.length() - 19);
            return new String[] { beginTimeStr, endTimeStr };
        } catch (Exception e) {
        }
        return new String[] { "1970-01-01 00:00:00", "2099-12-31 23:59:59" };
    }

    public static String formatDayRange(Long start, Long end) {
        return getDayFormat().format(new Date(start * 1000)) + " - " + getDayFormat().format(new Date(end * 1000));
    }

    public static String getChineseTimeDiff(Long time) {
        StringBuilder result = new StringBuilder();
        result.append(time / 86400 + "天");
        time = time % 86400;
        result.append(time / 3600 + "小时");
        time = time % 3600;
        result.append(time / 60 + "分钟");
        time = time % 60;
        result.append(time + "秒");
        return result.toString();
    }

    public static void main(String args[]) {
        Long current = TimeUtil.getCurrentSeconds();
        System.out.println(current);
        System.out.println(TimeUtil.formatDay(current));
        System.out.println(TimeUtil.formatTime(current));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Long endtime = TimeUtil.getCurrentSeconds();
        System.out.println(TimeUtil.getChineseTimeDiff(endtime - current));
        String range = TimeUtil.formatDayRange(current, endtime);
        System.out.println(range);
        Long[] ls = TimeUtil.splitDayRangeBig(range);
        System.out.println(ls[0] + "," + ls[1]);
        System.out.println(TimeUtil.formatDayRange(ls[0], ls[1]));
    }
}