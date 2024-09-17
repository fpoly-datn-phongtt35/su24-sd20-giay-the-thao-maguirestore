package com.datn.maguirestore.service.dto2;

import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.dto.SizeDTO;
import com.datn.maguirestore.entity.Shoes;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<ShoesDetailsDTO> shoesDetails;
    private List<ShoesDetailsCustomeDTO> shoesDetailsCustomeDTOS;
    private Set<SizeDTO> sizeDTOS;
    private Set<ColorDTO> colorDTOS;
    public ShoesDTO(Shoes shoes) {
        this.id = shoes.getId();
        this.code = shoes.getCode();
        this.name = shoes.getName();
        this.lastModifiedBy = shoes.getLastModifiedBy();
        this.lastModifiedDate = shoes.getLastModifiedDate();
    }
}
