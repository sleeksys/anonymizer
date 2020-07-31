package com.sleeksys.app.anonymizer.security.repositories;

import com.sleeksys.app.anonymizer.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findByUsername(String username);
}
