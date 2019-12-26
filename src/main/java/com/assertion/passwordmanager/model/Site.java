package com.assertion.passwordmanager.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "site")
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String siteName;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Password> passwords;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }
}
