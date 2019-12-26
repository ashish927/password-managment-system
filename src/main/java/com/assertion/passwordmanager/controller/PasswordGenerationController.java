package com.assertion.passwordmanager.controller;

import com.assertion.passwordmanager.model.PasswordGenerationRequest;
import com.assertion.passwordmanager.model.Site;
import com.assertion.passwordmanager.service.PasswordGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PasswordGenerationController {
    private PasswordGenerationService passwordGenerationService;

    @Autowired
    public PasswordGenerationController(PasswordGenerationService passwordGenerationService){
        this.passwordGenerationService = passwordGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity generatePassword(@RequestBody PasswordGenerationRequest passwordGenerationRequest){
        String response =  passwordGenerationService.generatePassword(passwordGenerationRequest);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity savePassword(@RequestBody PasswordGenerationRequest passwordGenerationRequest){
        Site response =  passwordGenerationService.savePassword(passwordGenerationRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity getAll(){
        List response =  passwordGenerationService.getAll();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllSite(){
        List response =  passwordGenerationService.getAllSite();
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
