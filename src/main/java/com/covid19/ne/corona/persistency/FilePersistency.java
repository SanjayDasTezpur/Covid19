package com.covid19.ne.corona.persistency;

/*
Created by sanjayda on 11/21/19 4:06 PM
*/

import com.covid19.ne.corona.entities.qualifiers.IPersistency;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FilePersistency<T extends IPersistency> {

    private String FILEPATH;
    private static FileInputStream fi;
    private static ObjectInputStream oi;
    private static FileOutputStream fileOut;
    private static ObjectOutputStream objectOut;

    public FilePersistency(String FILEPATH) {
        this.FILEPATH = FILEPATH;
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
            createIfNotExists(FILEPATH);
            deleteIfExists(FILEPATH + fileName);
            fileOut = new FileOutputStream(FILEPATH + fileName);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            log.info("The Object  was succesfully written to a file persistency");
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


    public <T> T getFromPersistency(String sFileName) {
        try {
            fi = new FileInputStream(new File(FILEPATH + sFileName));
            oi = new ObjectInputStream(fi);

            return (T) oi.readObject();
        } catch (Exception ex) {
            log.error("Error while fetching persistency from File System {}", ex.getMessage());
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
        File directory = new File(FILEPATH + sPath);
        return directory.exists();
    }
}
