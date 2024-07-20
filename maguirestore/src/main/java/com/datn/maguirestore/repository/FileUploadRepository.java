package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.FileUpload;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

//  Optional<FileUpload> findByFilePath(String path);

  Optional<FileUpload> findFirstByFilePath(String path);

}
