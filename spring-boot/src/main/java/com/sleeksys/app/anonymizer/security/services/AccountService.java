package com.sleeksys.app.anonymizer.security.services;


import com.sleeksys.app.anonymizer.security.entities.AppRole;
import com.sleeksys.app.anonymizer.security.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole saveAppRole(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username, String roleName);
}
