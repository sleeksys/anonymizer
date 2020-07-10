package com.sleeksys.app.anonymizer.repository;

import com.sleeksys.app.anonymizer.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CellRepository extends JpaRepository<Cell, Long> {
}
