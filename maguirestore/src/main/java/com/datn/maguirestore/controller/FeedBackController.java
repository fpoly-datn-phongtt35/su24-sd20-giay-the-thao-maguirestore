package com.datn.maguirestore.controller;


import com.datn.maguirestore.dto.AddressDTO;
import com.datn.maguirestore.dto.FeedBackDTO;
import com.datn.maguirestore.repository.FeedBackRepository;
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
@RequestMapping("/api/v1/feed-backs")
public class FeedBackController {

    private static final String ENTITY_NAME = "feedBack";
    private final Logger log = LoggerFactory.getLogger(FeedBackController.class);
    private final FeedBackService feedBackService;
    private final FeedBackRepository feedBackRepository;

    public FeedBackController(FeedBackService feedBackService, FeedBackRepository feedBackRepository) {
        this.feedBackService = feedBackService;
        this.feedBackRepository = feedBackRepository;
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackDTO feedBackDTO)
            throws URISyntaxException {
        log.debug("REST request to save Address : {}", feedBackDTO);

        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBacks() {
        log.debug("REST request to get all Addresses");
        return ResponseEntity.ok(feedBackService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<FeedBackDTO> updateFeedBacks(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody FeedBackDTO feedBackDTO)
            throws URISyntaxException {
        log.debug("REST request to update Brand : {}, {}", id, feedBackDTO);
        feedBackDTO.setId(id);
        FeedBackDTO result = feedBackService.update(feedBackDTO);
        return ResponseEntity.ok().body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<FeedBackDTO> getFeedbackById(@PathVariable Long id) {
        log.debug("REST request to get feedback by id");

        return ResponseEntity.ok(feedBackService.findbyId(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedBacks(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        feedBackService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status-update")
    public ResponseEntity<FeedBackDTO> updateStatusFeedBack(@RequestParam Integer id, @RequestParam Integer status)
            throws URISyntaxException {
        log.debug("REST request to update FeedBack : {}, {}", id, status);
//        if (id == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
        Long idd = Long.valueOf(id.longValue());
//        if (!feedBackRepository.existsById(idd)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }

        FeedBackDTO result = feedBackService.updateFeedbackStatus(idd, status);
        return ResponseEntity
                .ok()
//                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .body(result);
    }

    @GetMapping("/count-unread")
    public ResponseEntity<Integer> getFeedBackComment() {
        log.debug("REST request to get count FeedBack : {}", 0);
        return ResponseEntity.ok().body(feedBackRepository.countAllByStatus(0));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getFeedBackCommentAll() {
        log.debug("REST request to get count FeedBack : {}", 0);
        return ResponseEntity.ok().body(feedBackRepository.count());
    }


}
