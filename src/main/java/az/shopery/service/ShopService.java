package az.shopery.service;

import az.shopery.model.dto.response.ShopResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserShopResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    SuccessResponseDto<UserShopResponseDto> getMyShop(String userEmail);
    SuccessResponseDto<Page<ShopResponseDto>> getAllShops(Pageable pageable);
    SuccessResponseDto<ShopResponseDto> getShopById(String shopId);
    SuccessResponseDto<ShopResponseDto> getShopByShopName(String shopName);
}
