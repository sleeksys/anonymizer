package com.sleeksys.app.anonymizer.security.repositories;



import com.sleeksys.app.anonymizer.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    public AppRole findByRole(String role);
}
