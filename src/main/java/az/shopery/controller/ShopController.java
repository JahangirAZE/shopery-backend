package az.shopery.controller;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserShopResponseDto;
import az.shopery.service.ShopService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('MERCHANT')")
    public ResponseEntity<SuccessResponseDto<UserShopResponseDto>> getMyShop(Principal principal) {
        return ResponseEntity.ok(shopService.getMyShop(principal.getName()));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrSearchShops(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            Pageable pageable) {
        if (id != null) {
            return ResponseEntity.ok(shopService.getShopById(id));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(shopService.getShopByShopName(name));
        }
        return ResponseEntity.ok(shopService.getAllShops(pageable));
    }
}
