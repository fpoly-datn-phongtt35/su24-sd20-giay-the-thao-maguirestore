package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class SizeDTO implements Serializable {


    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public SizeDTO() {
    }

    public SizeDTO(Size size) {
        this.id = size.getId();
        this.code = size.getCode();
        this.name = size.getName();
        this.lastModifiedBy = size.getLastModifiedBy();
        this.lastModifiedDate = size.getLastModifiedDate();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SizeDTO)) {
            return false;
        }

        SizeDTO sizeDTO = (SizeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sizeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SizeDTO{" +
                "id=" + getId() +
                ", code='" + getCode() + "'" +
                ", name='" + getName() + "'" +
                ", status=" + getStatus() +
                ", createdBy='" + getCreatedBy() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", lastModifiedBy='" + getLastModifiedBy() + "'" +
                ", lastModifiedDate='" + getLastModifiedDate() + "'" +
                "}";
    }
}
