package com.datn.maguirestore.service.dto2;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.dto.SizeDTO;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesDetailsCustomeDTO {

    private Long id;

    private String code;

    private BigDecimal price;

    private BigDecimal import_price;

    private BigDecimal tax;

    private Long quantity;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesDTO shoes;

    private BrandDTO brand;

    private SizeDTO size;

    private List<SizeDTO> sizes;

    private ColorDTO color;
}
