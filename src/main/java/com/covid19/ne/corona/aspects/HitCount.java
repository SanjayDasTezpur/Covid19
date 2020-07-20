package com.covid19.ne.corona.aspects;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.aspects */

import com.covid19.ne.corona.uitility.DateUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HitCount {
    private static final ConcurrentHashMap<String, Integer> hitCountMap = new ConcurrentHashMap<>();

    public static void hit() {
        String date = DateUtility.getDate();
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

    public static Object getHitDetails() {
        int hitTotalCount = HitCount.getHitTotalCount();
        Map<String, Integer> hitDetails = new HashMap<>(hitCountMap);
        Map<String, Object> mp = new HashMap<>();
        mp.put("totalCount", hitTotalCount);
        mp.put("details", hitDetails);
        return mp;
    }
}


