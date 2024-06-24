package com.datn.maguirestore.controller;


import com.datn.maguirestore.dto.FeedBackDTO;
import com.datn.maguirestore.service.FeedBackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feed-back")
public class FeedBackController {

    private static final String ENTITY_NAME = "feedBack";
    private final Logger log = LoggerFactory.getLogger(FeedBackController.class);
    private final FeedBackService feedBackService;


    public FeedBackController(FeedBackService feedBackService) {
        this.feedBackService = feedBackService;
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/feed-backs")
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackDTO feedBackDTO)
            throws URISyntaxException {
        log.debug("REST request to save Address : {}", feedBackDTO);

        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/feed-backs")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBacks() {
        log.debug("REST request to get all Addresses");
        return ResponseEntity.ok(feedBackService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/feed-backs/{id}")
    public ResponseEntity<FeedBackDTO> updateFeedBacks(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody FeedBackDTO feedBackDTO)
            throws URISyntaxException {
        log.debug("REST request to update Brand : {}, {}", id, feedBackDTO);
        feedBackDTO.setId(id);
        FeedBackDTO result = feedBackService.update(feedBackDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/feed-backs/{id}")
    public ResponseEntity<Void> deleteFeedBacks(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        feedBackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
