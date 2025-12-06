package az.shopery.controller;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserProfileResponseDto;
import az.shopery.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/customers")
    public ResponseEntity<SuccessResponseDto<Page<UserProfileResponseDto>>> getCustomers(Pageable pageable) {
        return ResponseEntity.ok(adminService.getCustomers(pageable));
    }

    @GetMapping("/merchants")
    public ResponseEntity<SuccessResponseDto<Page<UserProfileResponseDto>>> getMerchants(Pageable pageable) {
        return ResponseEntity.ok(adminService.getMerchants(pageable));
    }
}
