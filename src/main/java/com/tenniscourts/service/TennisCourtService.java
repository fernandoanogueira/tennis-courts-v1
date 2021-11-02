package com.tenniscourts.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.entity.TennisCourt;
import com.tenniscourts.repository.TennisCourtRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TennisCourtService {

	@Autowired
    private final TennisCourtRepository tennisCourtRepository;

	@Autowired
    private final ScheduleService scheduleService;

	@Autowired
    private final ModelMapper modelMapper;

    public TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourt) {
        return modelMapper.map(tennisCourtRepository.saveAndFlush(modelMapper.map(tennisCourt, TennisCourt.class)), TennisCourtDTO.class);
    }

    public TennisCourtDTO findTennisCourtById(Long id) {
        return modelMapper.map(tennisCourtRepository.findById(id).get(),TennisCourtDTO.class);
    }

    public TennisCourtDTO findTennisCourtWithSchedulesById(Long tennisCourtId) {
        TennisCourtDTO tennisCourtDTO = findTennisCourtById(tennisCourtId);
        tennisCourtDTO.setTennisCourtSchedules(scheduleService.findSchedulesByTennisCourtId(tennisCourtId));
        return tennisCourtDTO;
    }
}
