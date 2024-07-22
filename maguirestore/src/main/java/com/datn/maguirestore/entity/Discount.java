package com.datn.maguirestore.entity;

import com.datn.maguirestore.dto.DiscountSearchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "discount_search_result",
                        classes = {
                                @ConstructorResult(
                                        targetClass = DiscountSearchDTO.class,
                                        columns = {
                                                @ColumnResult(name = "id", type = Long.class),
                                                @ColumnResult(name = "code", type = String.class),
                                                @ColumnResult(name = "name", type = String.class),
                                                @ColumnResult(name = "discountMethod", type = Integer.class),
                                                @ColumnResult(name = "discountMethodName", type = String.class),
                                                @ColumnResult(name = "status", type = String.class),
                                                @ColumnResult(name = "startDate", type = Instant.class),
                                                @ColumnResult(name = "endDate", type = Instant.class),
                                                @ColumnResult(name = "lastModifiedDate", type = Instant.class),
                                                @ColumnResult(name = "lastModifiedBy", type = String.class),
                                        }
                                ),
                        }
                ),
        }
)
@Entity
@Table(name = "discount")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount extends AbstractAuditingEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "discount_amount", precision = 21, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "discount_method")
    private Integer discountMethod;

    @Column(name = "status")
    private Integer status;
}
