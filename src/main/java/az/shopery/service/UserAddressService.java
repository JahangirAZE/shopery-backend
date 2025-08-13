package az.shopery.service;

import az.shopery.model.dto.request.AddressRequestDto;
import az.shopery.model.dto.response.AddressResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import java.util.List;

public interface UserAddressService {
    SuccessResponseDto<AddressResponseDto> add(String userEmail, AddressRequestDto dto);
    SuccessResponseDto<AddressResponseDto> update(String userEmail, String addressId, AddressRequestDto dto);
    SuccessResponseDto<Void> remove(String userEmail, String addressId);
    SuccessResponseDto<Void> setDefault(String userEmail, String addressId);
    SuccessResponseDto<List<AddressResponseDto>> getAll(String userEmail);
}
