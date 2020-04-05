package com.covid19.ne.corona.service;

/* sanjayda created on 4/5/2020 inside the package - com.covid19.ne.corona.service */

import com.covid19.ne.corona.entities.GraphData;
import com.covid19.ne.corona.entities.YData;
import com.covid19.ne.corona.entities.qualifiers.IPersistency;
import com.covid19.ne.corona.repositories.Covid19Repository;
import com.covid19.ne.corona.service.ifaces.IGraphPlotService;
import com.covid19.ne.corona.uitility.DateUtility;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphDataService implements IGraphPlotService {
    private final Covid19Repository repository;

    @Value("${covid19.lastTillDay:7}")
    private String lastTillDay;

    public GraphData performGraphDataMaking() {
        try {
            Gson gson = new Gson();
            GraphData result = new GraphData();
            Set<IPersistency> all = getLastTillDayData(repository.getAll());
            Set<Date> dates = getCollectXData(all);
            result.setXData(dates);
            List<YData> lstYdata = new ArrayList<>();
            result.setYData(lstYdata);
            List<Set> data1 = all.stream().map(data -> gson.fromJson(data.getDataInJson(), Map.class)).map(map -> map.get("data"))
                    .map(mappedData -> (Map) mappedData)
                    .map(stateMap -> stateMap.entrySet())
                    .collect(Collectors.toList());
            data1.stream().forEach(setData -> parseDistrictData(setData, lstYdata));
            result.proessData();
            return result;
        } catch (Exception e) {
            log.error("Error in performGraphDataMaking() - {} ", e.getMessage());
        }
        return new GraphData();
    }

    private Set<Date> getCollectXData(Set<IPersistency> persistencySet) {
        return persistencySet.stream().map(IPersistency::getDate)
                .map(dateStr -> {
                    try {
                        return DateUtility.getGlobalSDF().parse(dateStr);
                    } catch (Exception e) {
                        log.error("Exception while converting date in performGraphDataMaking() - {}", e.getMessage());
                        return null;
                    }
                })
                .filter(mpDate -> !Objects.isNull(mpDate)).distinct().collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<IPersistency> getLastTillDayData(Set<IPersistency> all) {
        int ilastTillDay = -getInteger(lastTillDay);
        Set<IPersistency> collect = all.stream().filter(persist -> {
            try {
                Date parse = DateUtility.getGlobalSDF().parse(persist.getDate());
                Calendar calobj = Calendar.getInstance();
                calobj.add(Calendar.DATE, ilastTillDay);
                return (parse.compareTo(calobj.getTime()) >= 0);
            } catch (Exception e) {
                log.error("Error in getLastTillDayData() - {}", e.getMessage());
            }
            return false;
        }).collect(Collectors.toCollection(TreeSet::new));
        return collect;
    }

    private void parseDistrictData(Set setData, List<YData> lstYdata) {
        setData.forEach(data -> {
            Object districtData = ((Map) ((Map.Entry) data).getValue()).get("districtData");
            Set set = ((Map) districtData).entrySet();
            set.stream().forEach(disData -> {
                String districtName = ((Map.Entry) disData).getKey().toString();
                String confirmedInStr = String.valueOf(((Map) ((Map.Entry) disData).getValue()).get("confirmed"));
                int confirmed = getInteger(confirmedInStr);
                Optional<YData> first = lstYdata.stream().filter(yData -> yData.getName().equalsIgnoreCase(districtName)).findFirst();
                if (first.isPresent()) {
                    YData yData = first.get();
                    List<Integer> data1 = yData.getData();
                    if (null == data1) {
                        List<Integer> lstConfirmedData = new ArrayList<>();
                        yData.setData(lstConfirmedData);
                        lstConfirmedData.add(confirmed);
                    } else {
                        data1.add(confirmed);
                    }
                } else {
                    YData yData = new YData();
                    lstYdata.add(yData);
                    yData.setName(districtName);
                    List<Integer> lstConfiremed = new ArrayList<>();
                    yData.setData(lstConfiremed);
                    lstConfiremed.add(confirmed);
                }
            });
            System.out.println(data);
        });
    }

    private int getInteger(String confirmedInStr) {
        try {
            return (int) Double.parseDouble(confirmedInStr);
        } catch (Exception e) {
            log.error("Error in getInteger() - {}", e.getMessage());
        }
        return 0;
    }

    @Override
    public GraphData getGraphPlotData() {
        return performGraphDataMaking();
    }

    class SortByDate implements Comparator<IPersistency> {
        @Override
        public int compare(IPersistency p1, IPersistency p2) {

            return 0;
        }
    }
}


