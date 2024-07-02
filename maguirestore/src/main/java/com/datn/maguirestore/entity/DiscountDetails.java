package com.datn.maguirestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "discount_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDetails extends AbstractAuditingEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnore
    private Discount discount;

    @ManyToOne
    @JsonIgnore
    private Shoes shoes;

}
