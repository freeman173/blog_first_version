package com.example.myadmin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String stampToTime(Long s) throws Exception{


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        long lt = new Long(s);

//将时间戳转换为时间

        Date date = new Date(lt);

//将时间调整为yyyy-MM-dd HH:mm:ss时间样式

        String res = simpleDateFormat.format(date);

        return res;

    }

}
