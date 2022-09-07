package com.loaning.loanrepay.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBProcessService {
    private final Logger logger = LoggerFactory.getLogger(DBProcessService.class);
    public void dumpDB(){
        List<String> commands = new ArrayList<String>();
        commands.add("./database_dumping.sh");
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        try{
            processBuilder.environment().put("PGPASSWORD","password");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
