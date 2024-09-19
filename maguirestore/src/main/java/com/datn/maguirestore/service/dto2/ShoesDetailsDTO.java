package com.datn.maguirestore.service.dto2;

import com.datn.maguirestore.dto.BrandDTO;
import com.datn.maguirestore.dto.ColorDTO;
import com.datn.maguirestore.dto.SizeDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesDetailsDTO implements Serializable {

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

    private ColorDTO color;

    private List<String> imgPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoesDetailsDTO)) {
            return false;
        }

        ShoesDetailsDTO shoesDetailsDTO = (ShoesDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoesDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoesDetailsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", shoes=" + getShoes() +
            ", brand=" + getBrand() +
            ", size=" + getSize() +
            ", color=" + getColor() +
            "}";
    }
}
