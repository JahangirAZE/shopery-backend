package az.shopery.controller;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.service.CustomerPhotoService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/customers/me/profile/photo")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CUSTOMER')")
public class CustomerPhotoController {

    private final CustomerPhotoService customerPhotoService;

    @PostMapping
    public ResponseEntity<SuccessResponseDto<String>> uploadMyProfilePhoto(
            Principal principal, @RequestParam("file") MultipartFile multipartFile) {
        return ResponseEntity.ok(customerPhotoService.uploadProfilePhoto(principal.getName(), multipartFile));
    }

    @GetMapping("/url")
    public ResponseEntity<SuccessResponseDto<String>> getMyProfilePhoto(
            Principal principal) {
        return ResponseEntity.ok(customerPhotoService.generatePresignedUrlForPhoto(principal.getName()));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponseDto<Void>> deleteMyProfilePhoto(
            Principal principal) {
        return ResponseEntity.ok(customerPhotoService.deleteProfilePhoto(principal.getName()));
    }
}
