package com.covid19.ne.corona.service;

/* sanjayda created on 4/2/2020 inside the package - com.covid19.ne.corona.service */

import com.covid19.ne.corona.entities.ResponseDto;
import com.covid19.ne.corona.operations.ResultPrepare;
import com.covid19.ne.corona.service.ifaces.ICovid19DataFetcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

import static com.covid19.ne.corona.constants.GlobalConstants.COMMA_SEPARATOR;

@Slf4j
@Service
@RequiredArgsConstructor
public class Covid19DataFetcherService implements ICovid19DataFetcher {

    @Value("${ne.states:Assam}")
    private String configuredStates;

    @Value("${covid19.url:https://api.covid19india.org}")
    private String url;

    @Value("${covid19.api.districWise:/state_district_wise.json}")
    private String districWise;

    @Value("${covid19.api.stateWiseV2:/data.json}")
    private String stateWiseV2 = "/data.json";

    private List<String> northEastStates;


    private RestTemplate restTemplate;

    @PostConstruct
    public void initService() {
        log.info("Initiating {} with root uri {} ...", getName(), this.url);
        this.restTemplate = new RestTemplate();
        northEastStates = new ArrayList<>(Arrays.asList(configuredStates.split(COMMA_SEPARATOR)));
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getDistrictWise() {
        log.info("getAllState()- Going to fetch all data");
        Optional<String> url = getURL(this.url, districWise);
        return url.map(this::httpGet).orElse(null);
    }

    @Override
    public String getAllStateV2() {
        log.info("getAllStateV2() - Going to fetch all data");
        Optional<String> url = getURL(this.url, stateWiseV2);
        return url.map(this::httpGet).orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getAllNEState() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> result = new TreeMap<>();
            Map<String, Object> districtWiseMap = mapper.readValue(getDistrictWise(), Map.class);
            Map<String, Object> stateWiseMap = mapper.readValue(getAllStateV2(), Map.class);
            Map<String, Object> overview = new TreeMap<>();
            String lastUpdatedTime = ResultPrepare.builder()
                    .result(result)
                    .districtResponse(districtWiseMap)
                    .stateResponse(stateWiseMap)
                    .overview(overview)
                    .northEastStates(northEastStates)
                    .build()
                    .invoke();
            return new ResponseDto(lastUpdatedTime, result, overview);
        } catch (IOException e) {
            log.error("Caught IOException: {} ", e.getMessage());
        } catch (Exception ex) {
            log.error("Caught Exception: {} ", ex.getMessage());
        }
        return "";
    }


    private String httpGet(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception ex) {
            log.error("Error in httpGet() - {}", ex.getMessage());
        }
        return null;
    }

    private static Optional<String> getURL(String uri, String api) {
        if (StringUtils.isEmpty(uri)) {
            log.error("Error getURL() - URI - first param cannot be empty");
            return Optional.empty();
        }
        if (StringUtils.isEmpty(api)) {
            log.warn("Warning getURL() - api is empty, returning only given URI");
            return Optional.of(uri);
        }
        StringBuilder sb = new StringBuilder(uri);
        return Optional.of(sb.append(api).toString());
    }
}


