package umc.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.dto.ReviewRequestDTO;
import umc.spring.service.reviewService.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping
    ApiResponse<Boolean> addReview(@RequestBody @Valid ReviewRequestDTO.JoinDTO request) {

        return ApiResponse.onSuccess(reviewService.addReview(request));
    }
}
