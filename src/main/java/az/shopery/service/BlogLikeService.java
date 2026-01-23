package az.shopery.service;

import az.shopery.model.dto.response.BlogResponseDto;
import az.shopery.model.dto.response.SuccessResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface BlogLikeService {
    SuccessResponseDto<Void> toggleBlogLike(String userEmail, String blogId);
    SuccessResponseDto<Page<BlogResponseDto>> getLikedBlogs(String userEmail, Pageable pageable);
}
