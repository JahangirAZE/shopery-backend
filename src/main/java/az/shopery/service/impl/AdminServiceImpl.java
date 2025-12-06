package az.shopery.service.impl;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserProfileResponseDto;
import az.shopery.model.entity.UserEntity;
import az.shopery.repository.UserRepository;
import az.shopery.service.AdminService;
import az.shopery.utils.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static az.shopery.utils.common.NameMapperHelper.first;
import static az.shopery.utils.common.NameMapperHelper.last;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Override
    public SuccessResponseDto<Page<UserProfileResponseDto>> getCustomers(Pageable pageable) {
        Page<UserEntity> customers = userRepository.findAllByUserRole(UserRole.CUSTOMER, pageable);
        return SuccessResponseDto.of(customers.map(this::mapToDto), "Customers are retrieved successfully");
    }

    @Override
    public SuccessResponseDto<Page<UserProfileResponseDto>> getMerchants(Pageable pageable) {
        Page<UserEntity> customers = userRepository.findAllByUserRole(UserRole.MERCHANT, pageable);
        return SuccessResponseDto.of(customers.map(this::mapToDto), "Merchants are retrieved successfully");
    }


    private UserProfileResponseDto mapToDto(UserEntity userEntity) {
        return UserProfileResponseDto.builder()
                .firstName(first(userEntity.getName()))
                .lastName(last(userEntity.getName()))
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .dateOfBirth(userEntity.getDateOfBirth())
                .profilePhotoUrl(userEntity.getProfilePhotoUrl())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }
}
