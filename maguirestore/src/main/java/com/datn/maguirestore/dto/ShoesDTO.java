package com.datn.maguirestore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesDTO implements Serializable {

    private Long id;
    @JsonIgnore
    private String code;
    private String name;
    @JsonIgnore
    private Integer status;
    private BrandDTO brand;
    private CategoryDTO category;

    @Override
    public String toString() {
        return "ShoesDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", brand=" + brand +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoesDTO)) {
            return false;
        }

        ShoesDTO shoesDTO = (ShoesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
