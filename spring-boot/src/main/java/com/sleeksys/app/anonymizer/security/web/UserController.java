package org.sid.taskmanagement.security.web;


import org.sid.taskmanagement.security.entities.AppUser;
import org.sid.taskmanagement.security.entities.RegistrationForm;
import org.sid.taskmanagement.security.services.AccountService;
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
