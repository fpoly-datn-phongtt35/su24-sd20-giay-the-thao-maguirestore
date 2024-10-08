package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Color;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesDetailsDTO implements Serializable {

    private Long id;

    private String code;

    private BigDecimal price;

    private BigDecimal importPrice;

    private BigDecimal tax;

    private Long quantity;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Shoes shoes;

    private Size size;

    private Color color;

    private List<String> imgPath;

    @Override
    public String toString() {
        return "ShoesDetailsDTO{" +
                "id=" + id +
                ", price=" + price +
                ", importPrice=" + importPrice +
                ", tax=" + tax +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", shoes=" + shoes +
                ", size=" + size +
                ", color=" + color +
                '}';
    }

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

}
