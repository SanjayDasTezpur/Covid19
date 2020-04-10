package com.covid19.ne.corona.persistency;

/*
Created by sanjayda on 11/21/19 4:06 PM
*/

import com.covid19.ne.corona.entities.qualifiers.IPersistency;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class FilePersistency<T extends IPersistency> {

    private final String directory;
    private static FileInputStream fi;
    private static ObjectInputStream oi;
    private static FileOutputStream fileOut;
    private static ObjectOutputStream objectOut;

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
        try {
            createIfNotExists(directory);
            deleteIfExists(directory + fileName);
            fileOut = new FileOutputStream(directory + fileName);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            log.info("The Object  was succesfully written to a file persistency {}", directory + fileName);
        } catch (Exception ex) {
            log.info("ERROR The Object  was not succesfully written to a file system persistency {}", ex.getMessage());
        } finally {
            try {
                fileOut.close();
                objectOut.close();
            } catch (Exception e) {
                log.error("Error while closing output streams [writting data] {}", e.getMessage());

            }
        }
    }

    public List getSavedObject() {
        File directory = new File(this.directory);
        return Arrays.stream(Objects.requireNonNull(directory.listFiles())).map(File::getPath).map(this::getFromPersistency).collect(Collectors.toList());
    }

    public <T> T getFromPersistency(String sFileName) {
        try {
            fi = new FileInputStream(new File(sFileName));
            oi = new ObjectInputStream(fi);

            return (T) oi.readObject();
        } catch (Exception ex) {
            log.error("Error in getFromPersistency() -  while fetching persistency from File System {}", ex.getMessage());
            return null;
        } finally {
            try {
                oi.close();
                fi.close();
            } catch (Exception e) {
                log.error("Error while closing input streams [getting data] {}", e.getMessage());
            }

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
            log.info("Directory deletion is {}", directory.delete() ? "sucessful" : "failed");
        }
    }

    public boolean checkIfExists(String sPath) {
        File directory = new File(this.directory + sPath);
        return directory.exists();
    }

    public String getTextFromSimpleFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        File file = new File(this.directory + fileName);
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException fnfe) {
            log.error("FileNotFoundException in getSimpleFile() - ");
        } catch (Exception e) {
            log.error("Error in getSimpleFile() - {}", e.getMessage());
        }

        return sb.toString();
    }
}
