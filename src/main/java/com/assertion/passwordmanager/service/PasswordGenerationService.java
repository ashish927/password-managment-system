package com.assertion.passwordmanager.service;

import com.assertion.passwordmanager.model.Password;
import com.assertion.passwordmanager.model.PasswordGenerationRequest;
import com.assertion.passwordmanager.model.Site;
import com.assertion.passwordmanager.repository.PasswordManagerRepository;
import com.assertion.passwordmanager.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PasswordGenerationService {

    private PasswordManagerRepository passwordManagerRepository;


    @Autowired
    public PasswordGenerationService(PasswordManagerRepository passwordManagerRepository) {
        this.passwordManagerRepository = passwordManagerRepository;
    }

    public String generatePassword(PasswordGenerationRequest passwordGenerationRequest){
        return PasswordUtil.generateCommonLangPassword();
    }

    public Site savePassword(PasswordGenerationRequest passwordGenerationRequest) {
        String pass = passwordGenerationRequest.getPassword();
        String coded = PasswordUtil.createCodedPasswordString(pass);
        String encrpt = PasswordUtil.encrypt(coded);
        Password passwordEntity = new Password();
        passwordEntity.setPassword(encrpt);
        Site site = new Site();
        site.setSiteName(passwordGenerationRequest.getSiteName());
        site.setPasswords(Arrays.asList(passwordEntity));
        return passwordManagerRepository.save(site);
    }

    public List getAllSite() {
        List<Site> sites = new ArrayList<>();
        List<Site> responseSite = new ArrayList<>();
        passwordManagerRepository.findAll().iterator().forEachRemaining(sites::add);
        sites.forEach(site -> {
            site.getPasswords().forEach(password -> {
                String decryptPassword = PasswordUtil.decrypt(password.getPassword());
                String decodedPassword = PasswordUtil.createDecodedPasswordString(decryptPassword);
                Password pass = new Password();
                pass.setPassword(decodedPassword);
                Site site1 = new Site();
                site1.setSiteName(site.getSiteName());
                site1.setPasswords(Arrays.asList(pass));
                responseSite.add(site1);
            });
        });
        return responseSite;
    }

    public List getAll() {
        List<Site> sites = new ArrayList<>();
        passwordManagerRepository.findAll().iterator().forEachRemaining(sites::add);
        return sites;
    }
}
