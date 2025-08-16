package az.shopery.controller;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserShopResponseDto;
import az.shopery.service.ShopService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    @PreAuthorize("hasAuthority('MERCHANT')")
    public ResponseEntity<SuccessResponseDto<UserShopResponseDto>> getMyShop(Principal principal) {
        return ResponseEntity.ok(shopService.getMyShop(principal.getName()));
    }
}
