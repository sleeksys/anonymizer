package com.sleeksys.app.anonymizer.repository;

import com.sleeksys.app.anonymizer.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}
