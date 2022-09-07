package com.loaning.loanrepay.controllers;

import com.loaning.loanrepay.services.DBProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/db")
@Tag(name = "DB", description = "This manages the db operations")
public class DBController {

    private DBProcessService dbProcessService;

    public DBController(DBProcessService dbProcessService) {
        this.dbProcessService = dbProcessService;
    }

    @GetMapping("/dump")
    public void generateDump(){
        dbProcessService.dumpDB();
    }
}
