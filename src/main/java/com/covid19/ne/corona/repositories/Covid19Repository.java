package com.covid19.ne.corona.repositories;

/* sanjayda created on 4/4/2020 inside the package - com.covid19.ne.corona.repositories */

import com.covid19.ne.corona.entities.qualifiers.DailyDataPersistenct;
import com.covid19.ne.corona.entities.qualifiers.IPersistency;
import com.covid19.ne.corona.persistency.FilePersistency;
import com.covid19.ne.corona.uitility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class Covid19Repository {

    public static final String FILE_SEPARATOR = "file.separator";
    public static final String SLASH = "/";
    public static final String UNDERSCORE = "_";
    private FilePersistency<IPersistency> filePersistency;

    @Value("${covid19.filePath:/home/sanjaydas_32100/workspace/covid19/data}")
    private String filePath;

    @PostConstruct
    public void initCovid19Repository() {
        log.info("Initiating {} ...", getClass().getSimpleName());
        this.filePersistency = new FilePersistency<>(filePath);
    }

    public void saveAll(Object data) throws Exception {
        DailyDataPersistenct dailyDataPersistenc = new DailyDataPersistenct(data, DateUtility.getDate());
        dailyDataPersistenc.makePersistable();
        getFilePath(dailyDataPersistenc.getDate()).ifPresent(path -> filePersistency.save(dailyDataPersistenc, path));
    }

    private Optional<String> getFilePath(String suffix) {
        if (StringUtils.isEmpty(suffix) || StringUtils.isEmpty(filePath)) {
            return Optional.empty();
        }
        StringBuilder sb = new StringBuilder();
        return Optional.of(sb.append(System.getProperty(FILE_SEPARATOR)).append(suffix.replaceAll(SLASH, UNDERSCORE)).toString());
    }
}


