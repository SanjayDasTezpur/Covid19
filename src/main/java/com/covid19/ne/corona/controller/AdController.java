package com.covid19.ne.corona.controller;

/* sanjayda created on 4/10/2020 inside the package - com.covid19.ne.corona.controller */

import com.covid19.ne.corona.entities.AdDto;
import com.covid19.ne.corona.service.Covid19DataFetcherService;
import com.covid19.ne.corona.service.ifaces.IAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdController {

    private final Covid19DataFetcherService covid19DataFetcherService;
    private final IAdService adService;

    @PostMapping("/auth/update/ad")
    public void updateAd(@RequestBody AdDto adDto) {
        log.info("Updating Advertisement");
        adService.updateAddDetails(adDto);
    }

    @GetMapping("/api/get/ad")
    public Object getAds() {
        return adService.getAddDetails();
    }

    @GetMapping("/auth/update/state")
    public Object addState(@RequestParam(value = "state") String state) {
        if (!Objects.isNull(state) && !state.trim().equalsIgnoreCase("")) {
            covid19DataFetcherService.getNorthEastStates().add(state);
            return covid19DataFetcherService.getNorthEastStates();
        }
        return "Not able to save new state " + state;
    }
}


