package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.PaymentDTO;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.repository.PaymentRepository;
import com.datn.maguirestore.service.EmailService;
import com.datn.maguirestore.service.PaymentService;
import com.datn.maguirestore.util.HeaderUtil;
import com.datn.maguirestore.util.PaginationUtil;
import com.datn.maguirestore.util.ResponseUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

  private static final String ENTITY_NAME = "payment";

  @Value("${clientApp.name}")
  private String applicationName;

  private final PaymentService paymentService;
  private final PaymentRepository paymentRepository;
//  private final OrderService orderService;
  private final EmailService mailService;

  @GetMapping("/create-payment")
  public String newPayments(
          @RequestParam("price") BigDecimal price,
          @RequestParam("receivedBy") String receivedBy,
          @RequestParam("phone") String phone,
          @RequestParam("email") String email,
          @RequestParam("address") String address,
          @RequestParam("province") Integer province,
          @RequestParam("district") Integer district,
          @RequestParam("ward") Integer ward,
          @RequestParam("shipPrice") BigDecimal shipPrice,
          @RequestParam("idOwner") String idOwner,
          @RequestParam("arrSanPham") String arrSanPham,
          @RequestParam("arrQuantity") String arrQuantity,
          @RequestParam("arrPriceDiscount") String arrPriceDiscount
  ) throws UnsupportedEncodingException {
    String paymentUrl = paymentService.createPayment(
            price,
            receivedBy,
            phone,
            email,
            address,
            province,
            district,
            ward,
            shipPrice,
            idOwner,
            arrSanPham,
            arrQuantity,
            arrPriceDiscount
    );
    return paymentUrl;
  }

  @PostMapping("")
  public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
    log.debug("REST request to save Payment : {}", paymentDTO);
    if (paymentDTO.getId() != null) {
      throw new BadRequestAlertException("A new payment cannot already have an ID", ENTITY_NAME, "idexists");
    }
    PaymentDTO result = paymentService.save(paymentDTO);
    return ResponseEntity
        .created(new URI("/api/payments/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        .body(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PaymentDTO> updatePayment(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody PaymentDTO paymentDTO
  ) throws URISyntaxException {
    log.debug("REST request to update Payment : {}, {}", id, paymentDTO);
    if (paymentDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, paymentDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!paymentRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    PaymentDTO result = paymentService.update(paymentDTO);
    return ResponseEntity
        .ok()
        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDTO.getId().toString()))
        .body(result);
  }

  @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
  public ResponseEntity<PaymentDTO> partialUpdatePayment(
      @PathVariable(value = "id", required = false) final Long id,
      @RequestBody PaymentDTO paymentDTO
  ) throws URISyntaxException {
    log.debug("REST request to partial update Payment partially : {}, {}", id, paymentDTO);
    if (paymentDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, paymentDTO.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!paymentRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<PaymentDTO> result = paymentService.partialUpdate(paymentDTO);

    return ResponseUtil.wrapOrNotFound(
        result,
        HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paymentDTO.getId().toString())
    );
  }

  @GetMapping("")
  public ResponseEntity<List<PaymentDTO>> getAllPayments(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
    log.debug("REST request to get a page of Payments");
    Page<PaymentDTO> page = paymentService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
        ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
    log.debug("REST request to get Payment : {}", id);
    Optional<PaymentDTO> paymentDTO = paymentService.findOne(id);
    return ResponseUtil.wrapOrNotFound(paymentDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
    log.debug("REST request to delete Payment : {}", id);
    paymentService.delete(id);
    return ResponseEntity
        .noContent()
        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }

}
