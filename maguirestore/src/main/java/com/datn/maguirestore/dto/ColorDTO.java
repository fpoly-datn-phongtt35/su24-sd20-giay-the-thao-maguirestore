package com.datn.maguirestore.dto;

import com.datn.maguirestore.entity.Color;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class ColorDTO implements Serializable {
    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public ColorDTO() {
    }

    public ColorDTO(Color color) {
        this.id = color.getId();
        this.code = color.getCode();
        this.name = color.getName();
        this.status = color.getStatus();
        this.createdBy = color.getCreatedBy();
        this.createdDate = color.getCreatedDate();
        this.lastModifiedBy = color.getLastModifiedBy();
        this.lastModifiedDate = color.getLastModifiedDate();
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
        if(!(o instanceof ColorDTO)) {
            return false;
        }

        ColorDTO colorDTO = (ColorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, colorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ColorDTO{" +
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
