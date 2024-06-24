package com.datn.maguirestore.service;

import com.datn.maguirestore.dto.FeedBackDTO;
import com.datn.maguirestore.entity.FeedBack;
import com.datn.maguirestore.repository.FeedBackRepository;
import com.datn.maguirestore.service.mapper.FeedBackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class FeedBackService {

    private final Logger log = LoggerFactory.getLogger(FeedBackService.class);

    private final FeedBackRepository feedBackRepository;

    private final FeedBackMapper feedBackMapper;

    public FeedBackService(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
    }

    public FeedBackDTO save(FeedBackDTO feedBackDTO) {
        log.debug("Request to save FeedBack : {}", feedBackDTO);
        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        return feedBackMapper.toDto(feedBack);
    }

    public FeedBackDTO update(FeedBackDTO feedBackDTO) {
        log.debug("Request to update FeedBack : {}", feedBackDTO);
        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        return feedBackMapper.toDto(feedBack);
    }



    @Transactional(readOnly = true)
    public List<FeedBackDTO> findAll() {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll().stream().map(feedBackMapper::toDto).collect(Collectors.toList());
    }


    public void delete(Long id) {
        log.debug("Request to delete FeedBack : {}", id);
        feedBackRepository.deleteById(id);
    }


}
