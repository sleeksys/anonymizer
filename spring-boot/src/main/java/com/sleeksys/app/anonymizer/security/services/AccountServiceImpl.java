package org.sid.taskmanagement.security.services;

import lombok.AllArgsConstructor;

import org.sid.taskmanagement.security.entities.AppRole;
import org.sid.taskmanagement.security.entities.AppUser;
import org.sid.taskmanagement.security.repositories.AppRoleRepository;
import org.sid.taskmanagement.security.repositories.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(String username, String password, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user !=null) throw new RuntimeException("User already exits");
        if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setActivated(true);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
        addRoleToUser(username, "USER");
        return appUser;
    }

    @Override
    public AppRole saveAppRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);

        System.out.println("AppUser" + " " + appUser);
        AppRole appRole = appRoleRepository.findByRole(roleName);
        appUser.getRoles().add(appRole);
    }
}
