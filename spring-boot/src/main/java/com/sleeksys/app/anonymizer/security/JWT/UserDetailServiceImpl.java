package com.sleeksys.app.anonymizer.security.JWT;


import com.sleeksys.app.anonymizer.security.entities.AppUser;
import com.sleeksys.app.anonymizer.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //look for User in the database
        AppUser appUser = accountService.loadUserByUsername(username);

        if(appUser ==null) throw new UsernameNotFoundException("User does not exists");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(r ->{
            authorities.add(new SimpleGrantedAuthority(r.getRole()));
        });

        return new User(appUser.getUsername(), appUser.getPassword(),authorities);
    }
}
