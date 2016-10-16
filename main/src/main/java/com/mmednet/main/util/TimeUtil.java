package com.mmednet.main.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alpha on 2016/9/22.
 */
public class TimeUtil {

    public static String getTimeSx() {
        Calendar cal=Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 8) {
            return "早上好";
        } else if (hour >= 8 && hour < 11) {
            return "上午好";
        } else if (hour >= 11 && hour < 13) {
            return "中午好";
        } else if (hour >= 13 && hour < 18) {
            return "下午好";
        } else {
            return "晚上好";
        }
    }

    public static int getAge(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return getAge(sdf.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAge(Date birthDate) {

        if (birthDate == null)
            throw new RuntimeException("出生日期不能为null");

        int age=0;

        Date now=new Date();

        SimpleDateFormat format_y=new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M=new SimpleDateFormat("MM");
        String birth_year=format_y.format(birthDate);
        String this_year=format_y.format(now);
        String birth_month=format_M.format(birthDate);
        String this_month=format_M.format(now);
        // 初步，估算
        age=Integer.parseInt(this_year) - Integer.parseInt(birth_year);
        // 如果未到出生月份，则age - 1
        if (this_month.compareTo(birth_month) < 0)
            age-=1;
        if (age < 0)
            age=0;
        return age;
    }
}
