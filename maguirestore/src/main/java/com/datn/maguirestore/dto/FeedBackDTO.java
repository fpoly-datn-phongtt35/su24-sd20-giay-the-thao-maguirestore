package com.datn.maguirestore.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FeedBackDTO implements Serializable {

    private Long id;

    private Integer rate;

    private String comment;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private UserDTO user;

    private ShoesDetailsDTO shoes;


}
