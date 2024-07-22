package com.datn.maguirestore.entity;

import com.datn.maguirestore.dto.OrderSearchResDTO;
import com.datn.maguirestore.dto.OrderStatusDTO;
import com.datn.maguirestore.dto.RevenueDTO;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(
    value = {
        @SqlResultSetMapping(
            name = "orders_result",
            classes = {
                @ConstructorResult(
                    targetClass = OrderSearchResDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "idCustomer", type = Long.class),
                        @ColumnResult(name = "customer", type = String.class),
                        @ColumnResult(name = "phone", type = String.class),
                        @ColumnResult(name = "receivedBy", type = String.class),
                        @ColumnResult(name = "totalPrice", type = BigDecimal.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "createdDate", type = Instant.class),
                        @ColumnResult(name = "lastModifiedBy", type = String.class),
                    }
                )
            }
        ),
        @SqlResultSetMapping(
            name = "orders_quantity_result",
            classes = {
                @ConstructorResult(
                    targetClass = OrderStatusDTO.class,
                    columns = {
                        @ColumnResult(name = "status", type = Integer.class), @ColumnResult(name = "quantity", type = Integer.class),
                    }
                )
            }
        ),
        @SqlResultSetMapping(
            name = "orders_revenue_result",
            classes = {
                @ConstructorResult(
                    targetClass = RevenueDTO.class,
                    columns = {
                        @ColumnResult(name = "month", type = Integer.class), @ColumnResult(name = "value", type = BigDecimal.class),
                    }
                )
            }
        )
    }
)
public class Order extends AbstractAuditingEntity<Long> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "address")
  private String address;

  @Column(name = "email_address")
  private String mailAddress;

  @Column(name = "phone")
  private String phone;

  @Column(name = "ship_price", precision = 21, scale = 2)
  private BigDecimal shipPrice;

  @Column(name = "total_price", precision = 21, scale = 2)
  private BigDecimal totalPrice;

  @Column(name = "received_by")
  private String receivedBy;

  @Column(name = "received_date")
  private Instant receivedDate;

  @Column(name = "shipped_date")
  private Instant shippedDate;

  @Column(name = "status")
  private Integer status;

  @ManyToOne
  private User owner;

  @ManyToOne
  private Payment payment;

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address userAddress;

  public Order(Long id) {
    this.id = id;
  }
}
