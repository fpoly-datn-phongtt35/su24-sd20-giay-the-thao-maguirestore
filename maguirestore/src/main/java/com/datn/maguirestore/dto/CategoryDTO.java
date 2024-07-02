package com.datn.maguirestore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Getter
@Setter
public class CategoryDTO implements Serializable {

    private Long id;

    @NotBlank(message = "error.shoes.category.code.notBlank")
    @JsonIgnore
    private String code;

    @NotBlank(message = "error.shoes.category.name.notBlank")
    private String name;

    @JsonIgnore
    private Integer status;

}
