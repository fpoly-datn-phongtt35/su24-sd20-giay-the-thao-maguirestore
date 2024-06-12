package com.datn.maguirestore.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Data
@Getter
@Setter
public class CategoryDTO implements Serializable {

    private Long id;

    @NotBlank(message = "error.shoes.category.code.notBlank")
    private String code;

    @NotBlank(message = "error.shoes.category.name.notBlank")
    private String name;

//    @Valid
//    private List<ShoesCategoryValueDTO> shoesCategoryValueDTOList;

    private Integer status;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
