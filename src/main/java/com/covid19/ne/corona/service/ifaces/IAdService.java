package com.covid19.ne.corona.service.ifaces;

import com.covid19.ne.corona.entities.AdDto;

/* sanjayda created on 4/2/2020 inside the package - com.covid19.ne.corona.service.ifaces */
public interface IAdService {
    String getName();

    Object getAddDetails();

    void updateAddDetails(AdDto adDto);
}


