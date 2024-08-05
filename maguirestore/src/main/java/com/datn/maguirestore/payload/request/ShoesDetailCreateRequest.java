package com.datn.maguirestore.payload.request;

import com.datn.maguirestore.entity.Color;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesDetailCreateRequest {
    private Long id;

    private Float price;

    private Float importPrice;

    private Float tax;

    private Integer quantity;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private Long shoesId;

    private Long sizeId;

    private Long colorId;
}
