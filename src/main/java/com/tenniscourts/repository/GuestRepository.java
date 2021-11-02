package com.tenniscourts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenniscourts.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
