package com.datn.maguirestore.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.datn.maguirestore.dto.FileUploadDTO;
import com.datn.maguirestore.entity.FileUpload;
import com.datn.maguirestore.repository.FileUploadRepository;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

  @Value("${aws.s3.bucketName}")
  private String bucketName;

  private final AmazonS3 s3Client;

  private final FileUploadRepository fileUploadRepository;

  public List<String> uploadAndConvertFiles(MultipartFile[] files) throws IOException {
    List<String> fileUrls = new ArrayList<>();

    for (MultipartFile file : files) {
      String originalFilename = UUID.randomUUID() + file.getOriginalFilename();

      if (originalFilename.isEmpty()) {
        log.error("Trống");
        throw new IllegalArgumentException("Lỗi không lấy được fileName");
      }

      String publicFileName = "images/" + originalFilename;

      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(file.getSize());
      metadata.setContentType(file.getContentType());

      s3Client.putObject(
          new PutObjectRequest(bucketName, publicFileName, file.getInputStream(), metadata));

      FileUpload fileUpload = new FileUpload();
      fileUpload.setFilePath(publicFileName);
      fileUpload.setFileName(originalFilename);
      fileUploadRepository.save(fileUpload);

      fileUrls.add(getPrivateFileUrl(publicFileName));
    }
    return fileUrls;
  }

  public String getPrivateFileUrl(String fileName) {
    return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-2",
        fileName);
  }

  public FileUpload save(FileUpload fileUpload) {
//    log.debug("Request to save FileUpload : {}", dto);
    System.out.println("save file upload");
//    FileUpload fileUpload = convertToEntity(dto);
    fileUpload = fileUploadRepository.save(fileUpload);
    return fileUpload;
  }

  public FileUploadDTO update(FileUploadDTO dto) {
    log.debug("Request to update FileUpload : {}", dto);
    FileUpload fileUpload = convertToEntity(dto);
    return convertToDto(fileUpload);
  }

  @Transactional(readOnly = true)
  public Optional<FileUpload> findByFilePath(String path) {
    log.debug("Request to get FileUpload : {}", path);
    return fileUploadRepository.findFirstByFilePath(path);
  }

  public FileUpload convertToEntity(FileUploadDTO dto) {
    if (dto == null) {
      return null;
    }
    String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
    FileUpload fileUpload = new FileUpload();
    fileUpload.setLastModifiedBy(loggedInUser);
    fileUpload.setLastModifiedDate(Instant.now());
    //fileUpload.setId( dto.getId() );
    fileUpload.setFilePath(dto.getFilePath());
    fileUpload.setFileName(dto.getFileName());
    fileUpload.setStatus(dto.getStatus());

    return fileUpload;
  }

  public FileUploadDTO convertToDto(FileUpload entity) {
    if (entity == null) {
      return null;
    }
    FileUploadDTO fileUploadDTO = new FileUploadDTO();
    //fileUploadDTO.setId( entity.getId() );
    fileUploadDTO.setFilePath(entity.getFilePath());
    fileUploadDTO.setFileName(entity.getFileName());
    fileUploadDTO.setStatus(entity.getStatus());

    return fileUploadDTO;
  }
}
