package com.covid19.ne.corona.operations;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.covid19.ne.corona.constants.GlobalConstants.*;

/* sanjayda created on 4/3/2020 inside the package - com.covid19.ne.corona.operations */

@Slf4j
@Builder
public class ResultPrepare {
    private final Map<String, Object> result;
    private final Map<String, Object> districtResponse;
    private final Map<String, Object> stateResponse;
    private final Map<String, Object> overview;
    private final List<String> northEastStates;

    public String invoke() {
        log.info("invoke()- preparing results ...");
        northEastStates.forEach(state -> {
            getOverAllSTateView(stateResponse, state).ifPresent(data -> {
                overview.put(state, data);
            });
        });
        String lastUpdatedTime = getLastUpdatedTime(stateResponse);
        districtResponse.entrySet().stream().filter(entry -> northEastStates.contains(entry.getKey()))
                .forEach(filteredEntry -> result.put(filteredEntry.getKey(), filteredEntry.getValue()));
        return lastUpdatedTime;
    }

    @SuppressWarnings("unchecked")
    private Optional<Object> getOverAllSTateView(Map allMap, String state) {
        try {

            return ((List) allMap.get(STATEWISE)).stream().filter(mp -> {
                return ((Map) mp).get("state").toString().equalsIgnoreCase(state);
            }).findFirst();

        } catch (Exception e) {
            log.error("Error in getOverAllSTateView() -  {}", e.getMessage());
        }
        return Optional.empty();
    }

    private String getLastUpdatedTime(Map<String, Object> allMap) {
        if (null == allMap) {
            return "";
        }
        if (null == allMap.get(KEY_VALUES) || ((List) allMap.get(KEY_VALUES)).size() == 0) {
            return "";
        }
        if (null == ((Map) (((List) allMap.get(KEY_VALUES)).get(0))).get(LASTUPDATEDTIME)) {
            return "";
        }
        return ((Map) (((List) allMap.get(KEY_VALUES)).get(0))).get(LASTUPDATEDTIME).toString();
    }
}


