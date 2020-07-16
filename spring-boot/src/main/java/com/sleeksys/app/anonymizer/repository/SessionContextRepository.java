package com.sleeksys.app.anonymizer.repository;

import com.sleeksys.app.anonymizer.entity.SessionContext;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface SessionContextRepository extends JpaRepository<SessionContext, Long> {
}
