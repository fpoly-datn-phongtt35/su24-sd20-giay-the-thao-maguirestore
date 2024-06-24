package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.ShoesDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Integer status;

    private Cart cart;

    private ShoesDetails shoesDetails;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @Override
    public String toString() {
        return "CartDTO{" +
                "id=" + id +
                ", quantity='" + quantity + '\'' +
                ", status=" + status + '\'' +
                ", cart=" + cart + '\'' +
                ", shoesDetails=" + shoesDetails + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartDetailsDTO)) {
            return false;
        }

        CartDetailsDTO cartDetailsDTO = (CartDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cartDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
