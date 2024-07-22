package com.datn.maguirestore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shoes_file_upload_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesFileUploadMapping extends AbstractAuditingEntity<Long> implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private FileUpload fileUpload;

    @ManyToOne
    private ShoesDetails shoesDetails;
}
