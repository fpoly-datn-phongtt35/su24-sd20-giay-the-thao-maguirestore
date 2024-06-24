package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.CartDetails;
import com.datn.maguirestore.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO implements Serializable {

    private Long id;

    private String code;

    private Integer status;

    private User user;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<CartDetails> cartDetailsList;
}
