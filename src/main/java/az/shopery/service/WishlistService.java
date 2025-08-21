package az.shopery.service;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.WishlistResponseDto;

public interface WishlistService {
    SuccessResponseDto<WishlistResponseDto> getMyWishlist(String userEmail);
    SuccessResponseDto<WishlistResponseDto> addProductToWishlist(String userEmail, String productId);
    SuccessResponseDto<WishlistResponseDto> removeProductFromWishlist(String userEmail, String productId);
}
