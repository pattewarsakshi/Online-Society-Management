package com.society.repository;



/* ========= SPRING DATA ========= */
import org.springframework.data.jpa.repository.JpaRepository;

/* ========= ENTITY ========= */
import com.society.entity.Visitor;

public interface VisitorRepository
        extends JpaRepository<Visitor, Integer> {
}
