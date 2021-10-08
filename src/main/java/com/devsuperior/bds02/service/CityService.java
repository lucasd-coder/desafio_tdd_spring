package com.devsuperior.bds02.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.respositories.CityRepository;
import com.devsuperior.bds02.service.exception.DataBaseException;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		List<City> result = cityRepository.findAll(Sort.by("name"));
		return result.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO upadate(Long id, CityDTO dto) {
		try {
			City entity = cityRepository.getOne(id);
			entity.setName(dto.getName());
			entity = cityRepository.save(entity);

			return new CityDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
		
	}

}
