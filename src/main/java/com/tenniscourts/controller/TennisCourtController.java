package com.tenniscourts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.TennisCourtDTO;
import com.tenniscourts.service.TennisCourtService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tennis-court/v1")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

	@ApiOperation(value="Creates a new tennis court")
    @PostMapping(value = "/tennis-court")
    public ResponseEntity<Void> addTennisCourt(TennisCourtDTO tennisCourtDTO) {
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(tennisCourtDTO).getId())).build();
    }

	@ApiOperation(value="Find a tennis court with schedules by id")
    @GetMapping(value = "/tennis-court")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

	@ApiOperation(value="Find a tennis court with schedules by id")
    @GetMapping(value = "/tennis-court/{tennisCourtId}/schedule")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
