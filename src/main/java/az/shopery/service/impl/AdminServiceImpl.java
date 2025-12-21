package az.shopery.service.impl;

import az.shopery.handler.exception.ResourceNotFoundException;
import az.shopery.model.dto.request.DeleteUserRequestDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserProfileResponseDto;
import az.shopery.model.entity.UserEntity;
import az.shopery.repository.UserRepository;
import az.shopery.service.AdminService;
import az.shopery.utils.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static az.shopery.utils.common.EnumUtils.formatRole;
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

    @Transactional
    @Override
    public SuccessResponseDto<Void> deleteUser(DeleteUserRequestDto deleteUserRequestDto, UserRole userRole) {
        String role = formatRole(userRole);
        String email = deleteUserRequestDto.getEmail();
        UserEntity user = userRepository.findByEmailAndUserRole(email, userRole).orElseThrow(
                () -> new ResourceNotFoundException(role + " not found")
        );
        userRepository.delete(user);
        return SuccessResponseDto.of(role + " deleted successfully");
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
