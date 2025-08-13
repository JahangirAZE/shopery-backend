package az.shopery.service;

import az.shopery.model.dto.response.SuccessResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserPhotoService {
    SuccessResponseDto<String> uploadProfilePhoto(String userEmail, MultipartFile multipartFile);
    SuccessResponseDto<String> generatePresignedUrlForPhoto(String userEmail);
    SuccessResponseDto<Void> deleteProfilePhoto(String userEmail);
}
