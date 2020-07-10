package com.sleeksys.app.anonymizer.repository;

import com.sleeksys.app.anonymizer.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface LabelRepository extends JpaRepository<Label, Long> {
}
