package az.shopery.service.impl;

import az.shopery.handler.exception.FileStorageException;
import az.shopery.handler.exception.ResourceNotFoundException;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.entity.CustomerEntity;
import az.shopery.repository.CustomerRepository;
import az.shopery.service.CustomerPhotoService;
import az.shopery.utils.aws.FileStorageService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerPhotoServiceImpl implements CustomerPhotoService {

    private final CustomerRepository customerRepository;
    private final FileStorageService fileStorageService;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    @Transactional
    public SuccessResponseDto<String> uploadProfilePhoto(String userEmail, MultipartFile multipartFile) {
        String fileKey = fileStorageService.store(multipartFile);
        CustomerEntity customerEntity = getCustomerByUserEmail(userEmail);

        customerEntity.setProfilePhotoUrl(fileKey);
        customerRepository.save(customerEntity);
        log.info("Saved profile photo key for customer {}: {}", userEmail, fileKey);

        return SuccessResponseDto.of(fileKey,
                "Profile photo uploaded successfully. Use key to get presigned URL.");
    }

    @Override
    @Transactional(readOnly = true)
    public SuccessResponseDto<String> generatePresignedUrlForPhoto(String userEmail) {
        CustomerEntity customerEntity = getCustomerByUserEmail(userEmail);

        String fileKey = customerEntity.getProfilePhotoUrl();
        if (fileKey == null || fileKey.isBlank()) {
            throw new ResourceNotFoundException("No profile photo found for customer: " + userEmail);
        }

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5))
                    .getObjectRequest(getObjectRequest)
                    .build();

            String presignedUrl = s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
            log.info("Generated presigned URL for key: {}", fileKey);

            return SuccessResponseDto.of(presignedUrl, "Presigned URL generated successfully.");
        } catch(Exception e) {
            log.error("Failed to generate presigned URL for key: {}", fileKey, e);
            throw new FileStorageException("Failed to generate presigned URL.", e);
        }
    }

    @Override
    @Transactional
    public SuccessResponseDto<Void> deleteProfilePhoto(String userEmail) {
        CustomerEntity customerEntity = getCustomerByUserEmail(userEmail);
        String fileKey = customerEntity.getProfilePhotoUrl();

        if (fileKey == null || fileKey.isBlank()) {
            log.info("User {} has no profile photo to delete.", userEmail);
            return SuccessResponseDto.of(null, "User does not have a profile photo.");
        }

        fileStorageService.delete(fileKey);
        customerEntity.setProfilePhotoUrl(null);
        customerRepository.save(customerEntity);
        log.info("Deleted profile photo for user {}", userEmail);

        return SuccessResponseDto.of(null, "Profile photo deleted successfully.");
    }

    private CustomerEntity getCustomerByUserEmail(String userEmail) {
        return customerRepository.findByUserEntityEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Customer profile not found for email: " + userEmail));
    }
}
