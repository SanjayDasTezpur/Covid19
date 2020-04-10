package com.covid19.ne.corona.service;

import com.covid19.ne.corona.entities.AdDto;
import com.covid19.ne.corona.service.ifaces.IAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/* sanjayda created on 4/10/2020 inside the package - com.covid19.ne.corona.service */

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements IAdService {

    private static final List<AdDto> lst = new ArrayList<>();

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Object getAddDetails() {
        return lst;
    }

    @Override
    public void updateAddDetails(AdDto adDto) {
        lst.add(adDto);
    }
}


