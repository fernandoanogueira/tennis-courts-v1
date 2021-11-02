package com.tenniscourts.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tenniscourts.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id);
    
    @Query(nativeQuery = true, value = "select * from Schedule where startDateTime between ? and ? ")
    List<Schedule> findByStartDateEndDate(LocalDateTime startDate, LocalDateTime endDate);
}