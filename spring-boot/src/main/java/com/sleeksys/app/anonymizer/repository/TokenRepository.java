package com.sleeksys.app.anonymizer.repository;

import com.sleeksys.app.anonymizer.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {
}
