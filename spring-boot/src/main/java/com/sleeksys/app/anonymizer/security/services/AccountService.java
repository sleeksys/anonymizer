package org.sid.taskmanagement.security.services;


import org.sid.taskmanagement.security.entities.AppRole;
import org.sid.taskmanagement.security.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(String username, String password, String confirmedPassword);
    public AppRole saveAppRole(AppRole role);
    public AppUser loadUserByUsername(String username);
    public void addRoleToUser(String username, String roleName);
}
