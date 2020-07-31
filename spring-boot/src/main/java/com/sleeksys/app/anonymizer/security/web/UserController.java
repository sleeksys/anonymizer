package com.sleeksys.app.anonymizer.security.web;


import com.sleeksys.app.anonymizer.security.entities.AppUser;
import com.sleeksys.app.anonymizer.security.entities.RegistrationForm;
import com.sleeksys.app.anonymizer.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/users")
    public AppUser register(@RequestBody RegistrationForm data){
        return accountService.saveUser(data.getUsername(),data.getPassword(),data.getRepassword());
    }
}
