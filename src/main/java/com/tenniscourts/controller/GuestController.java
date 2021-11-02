package com.tenniscourts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.service.GuestService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tennis-court/v1")
@AllArgsConstructor
public class GuestController extends BaseRestController {

	@Autowired
    private final GuestService guestService;

	@ApiOperation(value="Creates a new guest")
    @PostMapping(value = "/guest")
    public ResponseEntity<Void> addGuest(@RequestParam(required = true) String guestName) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestName).getId())).build();
    }

	@ApiOperation(value="Find guest by id")
	@GetMapping(value = "/guest/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@RequestParam(required = true) Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }
	
	@ApiOperation(value="Find all guests")
	@GetMapping(value = "/guest")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }
	
	@ApiOperation(value="Updates a guest")
    @PutMapping(value = "/guest")
    public ResponseEntity<Void> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.updateGuest(guestDTO).getId())).build();
    }
}
