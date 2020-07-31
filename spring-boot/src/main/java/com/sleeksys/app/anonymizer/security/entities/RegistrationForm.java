package org.sid.taskmanagement.security.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegistrationForm {
    String username;
    String password;
    String repassword;
}
