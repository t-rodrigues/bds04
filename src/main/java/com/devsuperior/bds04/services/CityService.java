package com.devsuperior.bds04.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.exceptions.BadRequestException;
import com.devsuperior.bds04.exceptions.ObjectNotFoundException;
import com.devsuperior.bds04.repositories.CityRepository;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAllOrderByName() {
        return cityRepository.findAllByOrderByNameAsc().stream().map(CityDTO::new).toList();
    }

    @Transactional
    public CityDTO create(CityDTO cityDTO) {
        var city = new City(null, cityDTO.getName());
        cityRepository.save(city);
        return new CityDTO(city);
    }

    public void delete(Long cityId) {
        var city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ObjectNotFoundException("City not found: " + cityId));

        try {
            cityRepository.delete(city);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
