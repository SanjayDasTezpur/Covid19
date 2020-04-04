package com.covid19.ne.corona.uitility;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.uitility */

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Slf4j
public class DateUtility {
    public static String getDate() {
        TimeZone time_zone = TimeZone.getTimeZone("IST");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calobj = Calendar.getInstance();
        calobj.setTimeZone(time_zone);
        return (df.format(calobj.getTime()));
    }

}


