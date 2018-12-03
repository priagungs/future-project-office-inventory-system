package com.future.office_inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class BackupRestore {

    @Autowired
    FileStorageService fileStorageService;

    @Value("${spring.datasource.username}")
    String dbusername;

    @Value("${spring.datasource.password}")
    String dbpassword;

    @Value("${postgres.host}")
    String dbhost;

    @Value("${postgres.dbname}")
    String dbname;

    public ResponseEntity backup() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String dateString = format.format(new Date());
        String filename = "backup_" + dateString + ".assist";

        processBuilder.command(
                "pg_dump",
                "--dbname=postgres://" + dbusername + ":" + dbpassword + "@" + dbhost + "/" + dbname,
                "--format=custom",
                "--verbose",
                "--file=static/" + filename);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (process.waitFor() != 0) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(process.getInputStream().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Resource resource = fileStorageService.loadFileAsResource(filename);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity restore(MultipartFile file) {
        String filename = fileStorageService.storeFile(file);
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(
                "pg_restore",
                "--dbname=postgres://" + dbusername + ":" + dbpassword + "@" + dbhost + "/" + dbname,
                "--format=custom",
                "--verbose",
                "--clean",
                "-n",
                "public",
                "static/" + filename);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (process.waitFor() != 0) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok().build();

    }

}
