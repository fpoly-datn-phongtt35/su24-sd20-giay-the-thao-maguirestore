package com.datn.maguirestore.controller;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.FileUpload;
import com.datn.maguirestore.entity.ShoesDetails;
import com.datn.maguirestore.entity.ShoesFileUploadMapping;
import com.datn.maguirestore.entity.Size;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.payload.request.ShoesDetailCreateRequest;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.repository.ShoesFileUploadMappingRepository;
import com.datn.maguirestore.repository.SizeRepository;
import com.datn.maguirestore.service.FileUploadService;
import com.datn.maguirestore.service.ShoesDetailsService;
import com.datn.maguirestore.service.ShoesFileUploadMappingService;
import com.datn.maguirestore.service.mapper.FileUploadMapper;
import com.datn.maguirestore.service.mapper.ShoesDetailsMapper;
import com.datn.maguirestore.util.AWSS3Util;
import com.datn.maguirestore.util.DataUtils;
import com.datn.maguirestore.util.HeaderUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/shoes-details")
@RequiredArgsConstructor
public class ShoesDetailsController {

    @Value("${clientApp.name}")
    private String applicationName;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private static final String ENTITY_NAME = "shoes-details";
    private final Logger log = LoggerFactory.getLogger(ShoesDetailsController.class);
    private final ShoesDetailsService shoesDetailsService;
    private final SizeRepository sizeRepository;
    private final FileUploadService fileUploadService;
    private final FileUploadMapper fileUploadMapper;
    private final ShoesFileUploadMappingService shoesFileUploadMappingService;
    private final ShoesFileUploadMappingRepository shoesFileUploadMappingRepository;

    private final ShoesDetailsRepository shoesDetailsRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping()
    public ResponseEntity<ShoesDetailsDTO> createShoesDetails(@RequestBody ShoesDetailCreateRequest request)
            throws URISyntaxException {
        log.debug("REST request to save ShoesDetails : {}", request);

        ShoesDetailsDTO result = shoesDetailsService.save(request);
        return ResponseEntity.created(new URI("/api/shoes-details/" + result.getId())).body(result);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<ShoesDetailsDTO> updateShoesDetails(
            @PathVariable(value = "id", required = false) final Long id, @RequestBody ShoesDetailsDTO shoesDetailsDTO)
            throws URISyntaxException {
        log.debug("REST request to update ShoesDetails : {}, {}", id, shoesDetailsDTO);

        ShoesDetailsDTO result = shoesDetailsService.update(shoesDetailsDTO);
        return ResponseEntity.ok().body(result);
    }

    //@SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public ResponseEntity<List<ShoesDetailsDTO>> getAllShoesDetails() {
        log.debug("REST request to get a page of ShoesDetails");
        List<ShoesDetailsDTO> page = shoesDetailsService.fillAll();
        for (ShoesDetailsDTO x : page) {
            ShoesDetails shoesDetails = ShoesDetailsMapper.INSTANCE.toEntity(x);
            x.setImgPath(getAllFilePathsForShoesDetails(shoesDetails));
        }
        return ResponseEntity.ok(page);
    }

    public List<String> getAllFilePathsForShoesDetails(ShoesDetails shoesDetails) {
        List<ShoesFileUploadMapping> mappings = shoesFileUploadMappingRepository.findByShoesDetails(shoesDetails);
        List<String> filePaths = mappings.stream().map(mapping -> mapping.getFileUpload().getFilePath()).collect(Collectors.toList());
        return filePaths;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<ShoesDetailsDTO> getById(@PathVariable Long id) {
        log.debug("REST request to get a page of ShoesDetails");
        return ResponseEntity.ok(shoesDetailsService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoesDetails(@PathVariable Long id) {
        log.debug("REST request to delete ShoesDetails : {}", id);
        shoesDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/shop")
    public ResponseEntity<List<ShopShoesDTO>> testing(@RequestBody SearchSDsResponse response) {
        List<Long> ids = response.getSizeIds();
        if (response.getSizeIds().isEmpty()) {
            ids = sizeRepository.findAll().stream().map(Size::getId).collect(Collectors.toList());
        }
        return ResponseEntity
                .ok()
                .body(
                        shoesDetailsRepository.findDistinctByShoesAndBrandOrderBySellPriceDesc(
                                ids,
                                response.getBrandId(),
                                response.getStartPrice(),
                                response.getEndPrice()
                        )
                );
    }

    @PostMapping("/shop/detail")
    public ResponseEntity<ShopShoesDTO> getShopShoesById(@RequestBody FindingOneDtos x) {
        ShopShoesDTO shopShoesDTO = shoesDetailsRepository.findDistinctByShoesAndBrandOrderBySellPriceDescOne(
                x.getShid(),
                x.getBrid(),
                x.getSiid(),
                x.getClid()
        );

//        System.out.println(shopShoesDTO.getBrandName());
        System.out.println(shopShoesDTO.getQuantity());

        if (shopShoesDTO != null) {
            return ResponseEntity.ok().body(shopShoesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/shoes-details-image")
    public ResponseEntity<ShoesDetailsDTO> createShoesDetailsImages(
            @RequestPart ShoesDetailsDTO shoesDetailsDTO,
            @RequestPart MultipartFile[] images
    ) throws URISyntaxException {
        // Kiểm tra nếu shoesDetailsDTO đã có ID
        if (shoesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // Lưu thông tin giày vào cơ sở dữ liệu
        ShoesDetailsDTO result = shoesDetailsService.save(shoesDetailsDTO);
        // Kiểm tra nếu mảng images là null
        if (images == null) {
            throw new BadRequestAlertException("Null image", ENTITY_NAME, "idexists");
        } else {
            // Lặp qua từng ảnh và thực hiện các bước lưu và tải lên S3
            for (MultipartFile image : images) {
                FileUpload fileUpload = uploadNewToS3(image);
                // Tạo ShoesFileUploadMappingDTO và lưu vào cơ sở dữ liệu
                ShoesFileUploadMappingDTO shoesFileUploadMappingDTO = new ShoesFileUploadMappingDTO(
                        Constants.STATUS.ACTIVE,
                        fileUpload,
                        result
                );
                ShoesFileUploadMappingDTO savedMapping = fileUploadMapping(shoesFileUploadMappingDTO);
            }
        }
        // Trả về thông báo tạo thành công và thông tin giày đã lưu
        return ResponseEntity
                .created(new URI("/api/v1/shoes-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/shoes-details-image")
    public ResponseEntity<ShoesDetailsDTO> updateShoesDetailsImages(
            @RequestPart ShoesDetailsDTO shoesDetailsDTO,
            @RequestPart(required = false) MultipartFile[] images
    ) throws URISyntaxException {
        // Kiểm tra nếu shoesDetailsDTO đã có ID
        if (shoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("A new shoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // Lưu thông tin giày vào cơ sở dữ liệu
        ShoesDetailsDTO result = shoesDetailsService.update(shoesDetailsDTO);
        // Kiểm tra nếu mảng images là null
        if (images != null && images.length > 0) {
            shoesFileUploadMappingRepository.deleteAllByShoesDetailsId(result.getId());
            // Lặp qua từng ảnh và thực hiện các bước lưu và tải lên S3
            for (MultipartFile image : images) {
                FileUpload fileUpload = uploadNewToS3(image);
                // Tạo ShoesFileUploadMappingDTO và lưu vào cơ sở dữ liệu
                ShoesFileUploadMappingDTO shoesFileUploadMappingDTO = new ShoesFileUploadMappingDTO(
                        Constants.STATUS.ACTIVE,
                        fileUpload,
                        result
                );
                ShoesFileUploadMappingDTO savedMapping = fileUploadMapping(shoesFileUploadMappingDTO);
            }
        }
        // Trả về thông báo tạo thành công và thông tin giày đã lưu
        return ResponseEntity
                .created(new URI("/api/v1/shoes-details/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @Transactional(rollbackFor = Exception.class)
    public FileUpload uploadNewToS3(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File fileOut = DataUtils.multipartFileToFile(file);
            // Build S3 path
            String s3Path = "images/" + file.getOriginalFilename();
            String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, "ap-southeast-2",
                    s3Path);
            // Check if the file with the same path exists in S3
            if (s3Url == null) {
                throw new IllegalArgumentException("s3Url is null");
            }
            Optional<FileUpload> existingFileUpload = fileUploadService.findByFilePath(s3Url);
            if (existingFileUpload.isPresent()) {
                // File already exists in S3, return the existing FileUploadDTO
                return existingFileUpload.get();
            } else {
                // File doesn't exist in S3, upload it and save FileUploadDTO
                FileUpload fileUpload = new FileUpload(null, s3Url, s3Path, Constants.STATUS.ACTIVE);
                FileUpload save = fileUploadService.save(fileUpload);
                // Upload to S3
                new AWSS3Util().uploadPhoto(s3Path, fileOut);
                return save;
            }
        } catch (IOException e) {
            // Handle the exception appropriately, log it, and rethrow or return null as needed
            log.error("Error while processing file for S3 upload", e);
            throw new RuntimeException("Error while processing file for S3 upload", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ShoesFileUploadMappingDTO fileUploadMapping(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        try {
            validateNewMapping(shoesFileUploadMappingDTO);
            ShoesFileUploadMappingDTO result = shoesFileUploadMappingService.save(shoesFileUploadMappingDTO);
            return result;
        } catch (BadRequestAlertException e) {
            // Handle the exception appropriately, log it, and rethrow or return null as needed
            log.error("Error while processing file upload mapping", e);
            throw e;
        } catch (Exception e) {
            // Handle unexpected exceptions, log them, and rethrow or return null as needed
            log.error("Unexpected error while processing file upload mapping", e);
            throw new RuntimeException("Unexpected error while processing file upload mapping", e);
        }
    }

    private void validateNewMapping(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        if (shoesFileUploadMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesFileUploadMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
    }

    @GetMapping("/new")
    public ResponseEntity<List<ShoesDetailDTOCustom>> getNewShoesDetail() {
        List<ShoesDetailDTOCustom> shoesDetailsDTOs = shoesDetailsService.getNewShoesDetail();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }

    @GetMapping("/newDiscount")
    public ResponseEntity<List<ShoesDetailDTOCustom>> getNewDiscountShoesDetail() {
        List<ShoesDetailDTOCustom> shoesDetailsDTOs = shoesDetailsService.getNewDiscountShoesDetail();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }

   /* @GetMapping("/bestseller")
    public ResponseEntity<List<ShoesDetailDTOCustom>> getBestSellerShoesDetail() {
        List<ShoesDetailDTOCustom> shoesDetailsDTOs = shoesDetailsService.getBestSellerShoesDetail();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }*/
}
