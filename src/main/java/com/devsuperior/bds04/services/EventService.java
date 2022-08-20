package com.devsuperior.bds04.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.exceptions.ObjectNotFoundException;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final CityRepository cityRepository;

    public EventService(EventRepository eventRepository, CityRepository cityRepository) {
        this.eventRepository = eventRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(EventDTO::new);
    }

    @Transactional
    public EventDTO update(Long eventId, EventDTO eventDTO) {
        var event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ObjectNotFoundException("Event not found: " + eventId));
        var city = cityRepository.findById(eventDTO.getCityId())
                .orElseThrow(() -> new ObjectNotFoundException("Event not found: " + eventId));
        event.setCity(city);
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setUrl(eventDTO.getUrl());
        eventRepository.save(event);
        return new EventDTO(event);
    }

}
