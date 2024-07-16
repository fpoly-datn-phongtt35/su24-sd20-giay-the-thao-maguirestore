package com.datn.maguirestore.payload.request;

import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesCreateRequest {
//    private Long id;

    private String code;

    private String name;

   // private String description;

   // private Integer status;

    //private String createdBy;

    //private Instant createdDate;

    private Long brandId;

    private Long categoryId;
}
