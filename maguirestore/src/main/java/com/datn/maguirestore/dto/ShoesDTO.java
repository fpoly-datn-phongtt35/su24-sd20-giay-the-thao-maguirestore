package com.datn.maguirestore.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ShoesDTO implements Serializable {

    private Long id;
    private String code;
    private String name;
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
       if (!(o instanceof ShoesDTO shoesDTO)) {
           return false;
       }

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
