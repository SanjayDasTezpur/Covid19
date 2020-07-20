package com.covid19.ne.corona.entities.qualifiers;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.entities.qualifiers */

import com.covid19.ne.corona.uitility.DateUtility;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Slf4j
@ToString
@RequiredArgsConstructor
public class DailyDataPersistenct implements IPersistency {

    @NonNull
    private final transient Object rawData;
    private final String date;

    private String dataInJson;


    @Override
    public void makePersistable() throws Exception {
        setDataInJson(new Gson().toJson(getRawData()));
    }

    @Override
    public int compareTo(IPersistency iPersistency) {
        try {
            SimpleDateFormat sdformat = DateUtility.getGlobalSDF();
            String persistencyDate = iPersistency.getDate();
            Date argDate = sdformat.parse(persistencyDate);
            Date objDate = sdformat.parse(this.getDate());
            return objDate.compareTo(argDate);
        } catch (ParseException e) {
            log.error("ParseException in compareTo() -  {}", e.getMessage());
        } catch (Exception ex) {
            log.error("Exception in compareTo() -  {}", ex.getMessage());
        }
        return -1;
    }
}


