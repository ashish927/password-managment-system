package com.assertion.passwordmanager.repository;

import com.assertion.passwordmanager.model.Site;
import org.springframework.data.repository.CrudRepository;

public interface PasswordManagerRepository extends CrudRepository<Site, Long> {
}
