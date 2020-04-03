package com.covid19.ne.corona.service.ifaces;

/* sanjayda created on 4/2/2020 inside the package - com.covid19.ne.corona.service.ifaces */
public interface ICovid19DataFetcher {
    String getName();

    String getDistrictWise();

    Object getAllNEState();

    String getAllStateV2();

    static String getDataFetchingVersion() {
        return "v1.0.0";
    }
}


