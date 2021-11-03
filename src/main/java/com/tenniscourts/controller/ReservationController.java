package com.tenniscourts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.service.ReservationService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api/tennis-court/v1")
@RestController
public class ReservationController extends BaseRestController {

	@Autowired
    private final ReservationService reservationService;

	@ApiOperation(value="Creates a new reservation")
    @PostMapping(value = "/reservation")
    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

	@ApiOperation(value="Find a reservation by id")
    @GetMapping(value = "/reservation")
    public ResponseEntity<ReservationDTO> findReservation(@RequestParam(required = true) Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

	@ApiOperation(value="Cancel a reservation")
    @PutMapping(value = "/reservation/{reservationId}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam(required = true) Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

	@ApiOperation(value="Reschedule a reservation")
	@PutMapping(value = "/reservation/{reservationId}/{scheduleId}/cancel")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam(required = true) Long reservationId, 
    		@RequestParam(required = true) Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
