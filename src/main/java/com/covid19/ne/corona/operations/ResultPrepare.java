package com.covid19.ne.corona.operations;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.covid19.ne.corona.constants.GlobalConstants.LASTUPDATEDTIME;
import static com.covid19.ne.corona.constants.GlobalConstants.STATEWISE;

/* sanjayda created on 4/3/2020 inside the package - com.covid19.ne.corona.operations */

@Slf4j
@Builder
public class ResultPrepare {
    public static final String STATE = "state";
    public static final String TOTAL = "Total";
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
        try {
            if (null == allMap) {
                return "";
            }
            if (null == allMap.get(STATEWISE) || ((List) allMap.get(STATEWISE)).size() == 0) {
                return "";
            }
            Optional first = ((List) allMap.get(STATEWISE)).stream().filter(stData -> {
                return ((Map) stData).get(STATE).toString().equalsIgnoreCase(TOTAL);
            }).findFirst();
            if (!first.isPresent()) {
                return "";
            }
            Object lastUpdated = ((Map) (first.get())).get(LASTUPDATEDTIME);
            if (null == lastUpdated) {
                return "";
            }
            return lastUpdated.toString();
        } catch (Exception e) {
            log.error("Error in getLastUpdatedTime() - {}", e.getMessage());
            return "";
        }
    }
}


