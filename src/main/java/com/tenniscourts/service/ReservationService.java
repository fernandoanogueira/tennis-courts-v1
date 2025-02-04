package com.tenniscourts.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.dto.CreateReservationRequestDTO;
import com.tenniscourts.dto.ReservationDTO;
import com.tenniscourts.entity.Reservation;
import com.tenniscourts.enums.ReservationStatus;
import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.repository.ReservationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservationService {

	@Autowired
    private final ReservationRepository reservationRepository;

	@Autowired
    private final ModelMapper modelMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
    	Reservation reservation = modelMapper.map(createReservationRequestDTO, Reservation.class);
    	reservation = reservationRepository.save(reservation);
    	return modelMapper.map(reservation, ReservationDTO.class);
    }

    public ReservationDTO findReservation(Long reservationId) {
        return modelMapper.map(reservationRepository.findById(reservationId), ReservationDTO.class);
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return modelMapper.map(this.cancel(reservationId), ReservationDTO.class);
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(modelMapper.map(previousReservation, ReservationDTO.class));
        return newReservation;
    }
}
