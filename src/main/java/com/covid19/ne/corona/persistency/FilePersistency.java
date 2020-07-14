package com.covid19.ne.corona.persistency;

/*
Created by sanjayda on 11/21/19 4:06 PM
*/

import com.covid19.ne.corona.entities.qualifiers.IPersistency;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FilePersistency<T extends IPersistency> {

    private final String directory;

    public FilePersistency(String directory) {
        this.directory = directory;
    }

    @Getter
    @Setter
    private boolean isDev;

    public void save(T serObj, String fileName) {

        if (isDev()) {
            log.info("The Object {} was succesfully written to a file {} ", serObj.toString(), fileName);
            return;
        }
        try (FileOutputStream fileOut = new FileOutputStream(directory + fileName);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            createIfNotExists(directory);
            deleteIfExists(directory + fileName);

            objectOut.writeObject(serObj);
            log.info("The Object  was succesfully written to a file persistency {}", directory + fileName);
        } catch (Exception ex) {
            log.info("ERROR The Object  was not succesfully written to a file system persistency {}", ex.getMessage());
        }
    }

    public List<Object> getSavedObject() {
        File loadedDirectory = new File(this.directory);
        return Arrays.stream(Objects.requireNonNull(loadedDirectory.listFiles())).map(File::getPath).map(FilePersistency::getFromPersistency).collect(Collectors.toList());
    }

    public static <T> T getFromPersistency(String sFileName) {
        try (FileInputStream fi = new FileInputStream(new File(sFileName));
             ObjectInputStream oi = new ObjectInputStream(fi)) {

            return (T) oi.readObject();
        } catch (Exception ex) {
            log.error("Error in getFromPersistency() -  while fetching persistency from File System {}", ex.getMessage());
            return null;
        }
    }

    public static void createIfNotExists(String sPath) {
        File directory = new File(sPath);
        if (!directory.exists()) {
            log.info("Directory creation is {}", directory.mkdirs() ? "sucessful" : "failed");
        }
    }

    public static void deleteIfExists(String sPath) {
        File directory = new File(sPath);
        if (!directory.exists()) {
            try {
                Files.delete(Paths.get(sPath));
                log.info("Directory deletion is sucessful");
            } catch (IOException e) {
                log.error("Directory deletion is failed");
            }
        }
    }

    public boolean checkIfExists(String sPath) {
        File loadedFile = new File(this.directory + sPath);
        return loadedFile.exists();
    }

    public String getTextFromSimpleFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        File file = new File(this.directory + fileName);
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fnfe) {
            log.error("FileNotFoundException in getSimpleFile() - ");
        } catch (Exception e) {
            log.error("Error in getSimpleFile() - {}", e.getMessage());
        }
        return null;

    }
}
