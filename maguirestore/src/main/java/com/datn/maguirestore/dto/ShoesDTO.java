package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Brand;
import com.datn.maguirestore.entity.Category;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class ShoesDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private Brand brand;

    private Category category;



    public ShoesDTO() {
    }

    public ShoesDTO(Long id, String code, String name, String description, Integer status, String createdBy, Instant createdDate, Brand brand, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.brand = brand;
        this.category = category;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }


    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ShoesDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
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
