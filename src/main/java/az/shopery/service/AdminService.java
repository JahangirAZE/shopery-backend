package az.shopery.service;

import az.shopery.model.dto.response.SuccessResponseDto;
import az.shopery.model.dto.response.UserProfileResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    SuccessResponseDto<Page<UserProfileResponseDto>> getCustomers(Pageable pageable);
    SuccessResponseDto<Page<UserProfileResponseDto>> getMerchants(Pageable pageable);
}
