package com.devsuperior.bds02.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.respositories.EventRepository;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional
	public EventDTO upadate(Long id, EventDTO dto) {
		try {
			Event entity = eventRepository.getOne(id);
			entity.setName(dto.getName());
			entity.setDate(dto.getDate());
			entity.setUrl(dto.getUrl());
			entity.getCity().setId(dto.getCityId());;
			entity = eventRepository.save(entity);

			return new EventDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}


}
