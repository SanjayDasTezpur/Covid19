package com.covid19.ne.corona.entities.qualifiers;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.entities.qualifiers */

import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
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
}


