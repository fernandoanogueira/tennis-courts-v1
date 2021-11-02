package com.tenniscourts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenniscourts.dto.GuestDTO;
import com.tenniscourts.entity.Guest;
import com.tenniscourts.exceptions.ResourceNotFoundException;
import com.tenniscourts.repository.GuestRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {

	@Autowired
    private final GuestRepository guestRepository;

	@Autowired
    private final ModelMapper modelMapper;

    public GuestDTO addGuest(String guestName) {
    	GuestDTO guest = new GuestDTO();
    	guest.setName(guestName);
        return modelMapper.map(guestRepository.save(modelMapper.map(guest, Guest.class)), GuestDTO.class);
    }

    public GuestDTO findGuestById(Long id) {
    	final Guest guest =  guestRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    			
        return modelMapper.map(guest,GuestDTO.class);
    }
    
    public void deleteGuest(Long id) {
    	final Guest guest =  guestRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        guestRepository.delete(guest);
    }

	public List<GuestDTO> findAll() {
		return guestRepository.findAll().stream().
				map(guest -> modelMapper.map(guest, GuestDTO.class)).
				collect(Collectors.toList());
	}
	
    public GuestDTO updateGuest(GuestDTO guestDTO) {
    	final Guest guest =  guestRepository.findById(guestDTO.getId())
    			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
    	guest.setName(guestDTO.getName());
    	
        return modelMapper.map(guestRepository.save(guest),GuestDTO.class);
    }
}
