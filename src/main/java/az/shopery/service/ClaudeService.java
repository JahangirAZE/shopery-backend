package az.shopery.service;

import az.shopery.model.dto.request.ChatRequestDto;
import az.shopery.model.dto.response.ChatResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;

public interface ClaudeService {
    SuccessResponseDto<ChatResponseDto> chat(String userEmail, ChatRequestDto chatRequestDto);
}
