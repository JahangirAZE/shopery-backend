package az.shopery.service;

import az.shopery.model.dto.response.CartResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;

public interface CartService {
    SuccessResponseDto<CartResponseDto> getMyCart(String userEmail);
    SuccessResponseDto<CartResponseDto> addProductToCart(String userEmail, String productId, int quantity);
    SuccessResponseDto<CartResponseDto> updateProductQuantity(String userEmail, String productId, int quantity);
    SuccessResponseDto<CartResponseDto> removeProductFromCart(String userEmail, String productId);
    SuccessResponseDto<CartResponseDto> moveProductFromWishlistToCart(String userEmail, String productId);
}
