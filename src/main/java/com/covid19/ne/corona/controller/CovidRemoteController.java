package com.covid19.ne.corona.controller;

/* sanjayda created on 4/2/2020 inside the package - com.covid19.ne.corona.controller */

import com.covid19.ne.corona.service.ifaces.ICovid19DataFetcher;
import com.covid19.ne.corona.service.ifaces.IGraphPlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CovidRemoteController {

    private final ICovid19DataFetcher service;
    private final IGraphPlotService graphPlotService;

    @GetMapping("/nestate/district")
    public Object getNEStateData() {
        return service.getAllNEState();
    }

    /*@GetMapping("/nestate/graphplot")
    public Object getGrap() {
        return graphPlotService.getGraphPlotData();
    }*/


}


