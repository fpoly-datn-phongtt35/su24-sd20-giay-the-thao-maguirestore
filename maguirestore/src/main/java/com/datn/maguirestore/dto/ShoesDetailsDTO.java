package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Color;
import com.datn.maguirestore.entity.Shoes;
import com.datn.maguirestore.entity.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class ShoesDetailsDTO implements Serializable {

    private Long id;

    private String code;

    private Float price;

    private Float importPrice;

    private Float tax;

    private Integer quantity;

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

    public ShoesDetailsDTO() {
    }

    public ShoesDetailsDTO(Long id, String code, Float price, Float importPrice, Float tax, Integer quantity, String description, Integer status, String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate, Shoes shoes, Size size, Color color) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.importPrice = importPrice;
        this.tax = tax;
        this.quantity = quantity;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.shoes = shoes;
        this.size = size;
        this.color = color;
    }

    public List<String> getImgPath() {
        return imgPath;
    }

    public void setImgPath(List<String> imgPath) {
        this.imgPath = imgPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(Float importPrice) {
        this.importPrice = importPrice;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
