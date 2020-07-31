package org.sid.taskmanagement.security.JWT;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Quand quelqu un veut s authentifier on appele cette methode
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // elle meme va rediriger vers la classe qui implemente userdetailservice
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // personaliser ma config
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
         // on desactive les sessions, spring ne garde plus rien en memoire. Notre session est le token
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.authorizeRequests().antMatchers("/login/**", "/users/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN");
         http.authorizeRequests().antMatchers("/appUsers/**","/appRoles/**").hasAuthority("ADMIN");
         http.authorizeRequests().anyRequest().authenticated();

         // Generer le token et renvoye au client
         http.addFilter(new JWTAuthentificationFilter(authenticationManager()));

        // verifier le token quand on evoit la requete
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
