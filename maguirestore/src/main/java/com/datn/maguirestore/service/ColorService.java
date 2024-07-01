package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.entity.Color;
import com.datn.maguirestore.repository.ColorRepository;
import com.datn.maguirestore.service.mapper.ColorMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ColorService {

    private final Logger log = LoggerFactory.getLogger(ColorService.class);

    private final ColorRepository colorRepository;

    private final ColorMapper colorMapper;

    public ColorDTO save(ColorDTO colorDTO) {
        log.debug("Request to save Color : {}", colorDTO);

        Color color = colorMapper.toEntity(colorDTO);
        color.setStatus(1);
        color = colorRepository.save(color);
        return colorMapper.toDto(color);
    }

    public ColorDTO update(ColorDTO colorDTO) {
        log.debug("Request to update Color : {}", colorDTO);

        Color color = colorMapper.toEntity(colorDTO);
        color.setStatus(1);
        color = colorRepository.save(color);
        return colorMapper.toDto(color);
    }

    @Transactional(readOnly = true)
    public ColorDTO findbyId(Long id) {
        log.debug("Request to get Color by id");

        return colorRepository.findById(id)
                .map(colorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Color not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<ColorDTO> findAll() {
        log.debug("Request to get all Color");

        return colorRepository.findByStatus(1).stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<ColorDTO> findAllByRemove() {
        log.debug("Request to get all Color");

        return colorRepository.findByStatus(0).stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void delete(Long id) {
        log.debug("Request to mark Color as inactive: {}", id);

        Optional<Color> colorOptional = colorRepository.findById(id);
        if(colorOptional.isPresent()) {
            Color color = colorOptional.get();
            color.setStatus(0);
            colorRepository.save(color);
        }
    }


}
