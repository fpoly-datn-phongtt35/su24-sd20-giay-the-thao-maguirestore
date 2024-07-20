package com.datn.maguirestore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileUploadDTO {
  private Long id;
  private String filePath;
  private String fileName;
  private Integer status;

}
