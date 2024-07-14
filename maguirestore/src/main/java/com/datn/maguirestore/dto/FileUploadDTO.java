package com.datn.maguirestore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileUploadDTO {
//  private Long id;
  private String path;
  private String name;
  private Integer status;

}
