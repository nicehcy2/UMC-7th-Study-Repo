package umc.spring.service.reviewService;

import umc.spring.dto.ReviewRequestDTO;

public interface ReviewService {

    boolean addReview(ReviewRequestDTO.JoinDTO request);
}
