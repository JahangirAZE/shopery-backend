package az.shopery.controller;

import az.shopery.model.dto.request.DeleteUserRequestDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserProfileResponseDto;
import az.shopery.service.AdminService;
import az.shopery.utils.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/customers")
    public ResponseEntity<SuccessResponseDto<Void>> deleteCustomer(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        return ResponseEntity.ok(adminService.deleteUser(deleteUserRequestDto, UserRole.CUSTOMER));
    }

    @DeleteMapping("/merchants")
    public ResponseEntity<SuccessResponseDto<Void>> deleteMerchant(@RequestBody DeleteUserRequestDto deleteUserRequestDto) {
        return ResponseEntity.ok(adminService.deleteUser(deleteUserRequestDto, UserRole.MERCHANT));
    }
}
