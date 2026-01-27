package com.society.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Flat;


public interface FlatRepository extends JpaRepository<Flat, Integer> {

}
