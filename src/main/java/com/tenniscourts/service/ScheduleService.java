package com.tenniscourts.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.dto.CreateScheduleRequestDTO;
import com.tenniscourts.dto.ScheduleDTO;
import com.tenniscourts.entity.Schedule;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.repository.ScheduleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

	@Autowired
    private final ScheduleRepository scheduleRepository;

	@Autowired
	private ModelMapper modelMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
		Schedule entity = modelMapper.map(createScheduleRequestDTO, Schedule.class);
		return modelMapper.map(scheduleRepository.save(entity), ScheduleDTO.class);
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleRepository.findByStartDateEndDate(startDate, endDate)
			.stream()
	        .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
	        .collect(Collectors.toList());
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
		var entity = scheduleRepository.findById(scheduleId)
				.orElseThrow(() -> new EntityNotFoundException("No records found for this ID"));
		return modelMapper.map(entity, ScheduleDTO.class);
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId)
	        .stream()
	        .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
	        .collect(Collectors.toList());
    }
}
