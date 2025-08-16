package az.shopery.service;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserShopResponseDto;

public interface ShopService {
    SuccessResponseDto<UserShopResponseDto> getMyShop(String userEmail);
}
