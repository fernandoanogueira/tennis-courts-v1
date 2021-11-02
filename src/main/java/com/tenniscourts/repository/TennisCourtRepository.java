package com.tenniscourts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenniscourts.entity.TennisCourt;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
