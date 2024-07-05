package com.datn.maguirestore.controller;

import com.datn.maguirestore.entity.FileUpload;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.repository.FileUploadRepository;
import com.datn.maguirestore.service.FileUploadService;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file-upload")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

  private static final String ENTITY_NAME = "fileUpload";

  @Value("${clientApp.name}")
  private String applicationName;

  private final FileUploadRepository fileUploadRepository;

  private final FileUploadService fileService;

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<?> uploadAndConvertFiles(@RequestPart("file") MultipartFile[] files) {
    try {
      List<String> fileUrls = fileService.uploadAndConvertFiles(files);

      // Trả về danh sách các URL public của các file đã upload
      return ResponseEntity
          .created(new URI("/api/v1/file-upload"))
          .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
              fileUrls.toString()))
          .body(fileUrls);
    } catch (IOException | URISyntaxException e) {
      return new ResponseEntity<>("Failed to upload and convert files",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  @PostMapping("")
//  public ResponseEntity<FileUpload> createFileUpload(@RequestBody FileUpload fileUpload)
//      throws URISyntaxException {
//    log.debug("REST request to create upload file", fileUpload);
//    if (null != fileUpload.getId()) {
//      throw new BadRequestAlertException("A new fileUpload cannot already have an ID", ENTITY_NAME,
//          "idexists");
//    }
//    FileUpload result = fileUploadRepository.save(fileUpload);
//    return ResponseEntity
//        .created(new URI("/api/v1/file-upload" + result.getId()))
//        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//        .body(result);
//  }

}
