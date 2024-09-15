package com.datn.maguirestore.dto;

import com.datn.maguirestore.util.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    private Long id;

    private String code;

    //    @NotNull
    private AddressDTO userAddress;

    @NotBlank
    private String phone;

    @NotNull
    private Integer paymentMethod;

    private Integer paymentStatus;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;
    private String mailAddress;

    private String receivedDate;
    private String address;
    private String shippedDate;

    private Integer status;
    private UserDTO owner;

    @NotEmpty(message = "Vui lòng chọn giày!")
    @Valid
    private List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();

    public Instant getReceivedDate() {
        return Objects.isNull(receivedDate) ? null : DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(receivedDate);
    }

    public Instant getShippedDate() {
        return Objects.isNull(receivedDate) ? null : DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(shippedDate);
    }
}
