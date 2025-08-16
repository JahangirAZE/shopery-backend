package az.shopery.service.impl;

import az.shopery.handler.exception.ResourceNotFoundException;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserShopResponseDto;
import az.shopery.model.entity.ShopEntity;
import az.shopery.repository.ShopRepository;
import az.shopery.repository.UserRepository;
import az.shopery.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    @Override
    public SuccessResponseDto<UserShopResponseDto> getMyShop(String userEmail) {
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        ShopEntity shopEntity = shopRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found for user: " + userEmail));

        var userShopResponseDto = UserShopResponseDto.builder()
                .shopName(shopEntity.getShopName())
                .description(shopEntity.getDescription())
                .totalIncome(shopEntity.getTotalIncome())
                .rating(shopEntity.getRating())
                .createdAt(shopEntity.getCreatedAt())
                .build();

        return SuccessResponseDto.of(userShopResponseDto, "User shop retrieved successfully.");
    }
}
