package com.covid19.ne.corona.service;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.service */

import com.covid19.ne.corona.repositories.Covid19Repository;
import com.covid19.ne.corona.service.ifaces.ICovid19DataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyCollectorService {

    private final ICovid19DataFetcher service;
    private final Covid19Repository repository;

    //@Scheduled(fixedDelayString = "${covid19.frequency:120000}", initialDelay = 60 * 1000)
    public void storeDataDaily() {
        dailyCollect();
    }

    private void dailyCollect() {
        try {
            log.info("Staring dailly collection ...");
            Object allNEState = service.getAllNEState();
            repository.saveAll(allNEState);
        } catch (Exception ex) {
            log.error("Failed in dailyCollect() - {}", ex.getMessage());
        }
    }
}


