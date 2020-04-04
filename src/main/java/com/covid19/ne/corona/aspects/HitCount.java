package com.covid19.ne.corona.aspects;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.aspects */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public final class HitCount {
    private static final ConcurrentHashMap<String, Integer> hitCountMap = new ConcurrentHashMap<>();

    public static void hit() {
        String date = getDate();
        if (hitCountMap.size() < 300) {
            hitCountMap.put(date, hitCountMap.get(date) == null ? 0 : hitCountMap.get(date) + 1);
        } else {
            int val = hitCountMap.get(date) + 1;
            hitCountMap.clear();
            hitCountMap.put(date, val);
        }

    }

    public static int getHitTotalCount() {
        return hitCountMap.values().stream().reduce(0, Integer::sum);
    }

    private static String getDate() {
        TimeZone time_zone = TimeZone.getTimeZone("IST");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calobj = Calendar.getInstance();
        calobj.setTimeZone(time_zone);
        return (df.format(calobj.getTime()));
    }

    public static Object getHitDetails() {
        int hitTotalCount = HitCount.getHitTotalCount();
        Map hitDetails = new HashMap(hitCountMap);
        Map<String, Object> mp = new HashMap<>();
        mp.put("totalCount", hitTotalCount);
        mp.put("details", hitDetails);
        return mp;
    }
}


