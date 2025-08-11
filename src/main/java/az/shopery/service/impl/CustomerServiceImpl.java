package az.shopery.service.impl;

import az.shopery.handler.exception.ResourceNotFoundException;
import az.shopery.model.dto.request.CustomerProfileUpdateRequestDto;
import az.shopery.model.dto.response.CustomerProfileResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.entity.CustomerEntity;
import az.shopery.model.entity.UserEntity;
import az.shopery.repository.CustomerRepository;
import az.shopery.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void createCustomerProfile(UserEntity userEntity) {
        String fullName = userEntity.getName();
        String[] names = fullName.trim().split("\\s+");
        CustomerEntity customerEntity = CustomerEntity.builder()
                .userEntity(userEntity)
                .firstName(names[0].trim())
                .lastName(names[1].trim())
                .build();
        customerRepository.save(customerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SuccessResponseDto<CustomerProfileResponseDto> getCustomerProfile(String userEmail) {
        CustomerEntity customerEntity = customerRepository.findByUserEntityEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer profile not found for email: " + userEmail));

        var customerResponse = mapToCustomerProfileResponseDto(customerEntity);

        return SuccessResponseDto.of(customerResponse, "Customer profile retrieved successfully.");
    }

    @Override
    @Transactional
    public SuccessResponseDto<CustomerProfileResponseDto> updateCustomerProfile(String userEmail, CustomerProfileUpdateRequestDto customerProfileUpdateRequestDto) {
        CustomerEntity customerEntity = customerRepository.findByUserEntityEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer profile not found for email: " + userEmail));

        customerEntity.setFirstName(customerProfileUpdateRequestDto.getFirstName());
        customerEntity.setLastName(customerProfileUpdateRequestDto.getLastName());
        customerEntity.setPhone(customerProfileUpdateRequestDto.getPhone());
        customerEntity.setDateOfBirth(customerProfileUpdateRequestDto.getDateOfBirth());

        CustomerEntity updatedCustomerEntity = customerRepository.save(customerEntity);
        log.info("Updated customer profile for user {}", userEmail);

        var customerResponse = mapToCustomerProfileResponseDto(updatedCustomerEntity);

        return SuccessResponseDto.of(customerResponse, "Customer profile updated successfully.");
    }

    private CustomerProfileResponseDto mapToCustomerProfileResponseDto(CustomerEntity customerEntity) {
        return CustomerProfileResponseDto.builder()
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .email(customerEntity.getUserEntity().getEmail())
                .phone(customerEntity.getPhone())
                .dateOfBirth(customerEntity.getDateOfBirth())
                .profilePhotoUrl(customerEntity.getProfilePhotoUrl())
                .createdAt(customerEntity.getCreatedAt())
                .build();
    }
}
