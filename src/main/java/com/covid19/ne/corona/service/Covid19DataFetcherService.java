package com.covid19.ne.corona.service;

/* sanjayda created on 4/2/2020 inside the package - com.covid19.ne.corona.service */

import com.covid19.ne.corona.entities.ResponseDto;
import com.covid19.ne.corona.service.ifaces.IService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class Covid19DataFetcherService implements IService {
    public static final String KEY_VALUES = "key_values";
    public static final String LASTUPDATEDTIME = "lastupdatedtime";
    public static final String STATEWISE = "statewise";
    String url = "https://api.covid19india.org";
    String districWise = "/state_district_wise.json";
    String districWiseV2 = "/data.json";
    List<String> northEastStates = new ArrayList<>(Arrays.asList("Assam","Arunachal Pradesh", "Mizoram", "Manipur"));

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAllState() {
        log.info("GOing to fetch all data");
        return restTemplate.getForObject(getURL(url, districWise), String.class);
    }

    public String getAllStateV2() {
        log.info("GOing to fetch all data");
        return restTemplate.getForObject(getURL(url, districWiseV2), String.class);
    }

    @SuppressWarnings("unchecked")
    public Object getAllNEState() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> result = new TreeMap<>();
            Map<String, Object> map = mapper.readValue(getAllState(), Map.class);
            Map<String, Object> allMap = mapper.readValue(getAllStateV2(), Map.class);
            Map<String, Object> overview = new TreeMap<>();
            northEastStates.forEach(state -> {
                getDeathByState(allMap, state).ifPresent(data -> {
                    overview.put(state, data);
                });
            });
            String lastUpdatedTime = getLastUpdatedTime(allMap);
            map.entrySet().stream().filter(entry -> northEastStates.contains(entry.getKey()))
                    .forEach(filteredEntry -> result.put(filteredEntry.getKey(), filteredEntry.getValue()));
            return new ResponseDto(lastUpdatedTime, result, overview);
        } catch (IOException e) {
            log.error("Caught IOException: {} ", e.getMessage());
        } catch (Exception ex) {
            log.error("Caught Exception: {} ", ex.getMessage());
        }
        return "";
    }

    @SuppressWarnings("unchecked")
    private Optional<Object> getDeathByState(Map allMap, String state) {
        return ((List) allMap.get(STATEWISE)).stream().filter(mp -> {
            return ((Map) mp).get("state").toString().equalsIgnoreCase(state);
        }).findFirst();
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

    private static String getURL(String uri, String api) {
        return uri + api;
    }

    @Override
    public String getName() {
        return Covid19DataFetcherService.class.getName();
    }
}


