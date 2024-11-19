package umc.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.spring.domain.Member;
import umc.spring.domain.Review;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO {
        Long memberId;
        LocalDateTime createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyReviewListDTO {

        String reviewBody;
        Float score;
        LocalDateTime createdAt;

        public static MyReviewListDTO of(Review review) {
            return MyReviewListDTO.builder()
                    .reviewBody(review.getBody())
                    .score(review.getScore())
                    .createdAt(review.getCreatedAt())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyReviewListResponseDTO {

        List<MyReviewListDTO> reviews;
        int reviewCount;
        String name;

        public static MyReviewListResponseDTO of(List<MyReviewListDTO> myReviewListDTOS, String memberName) {
            return MyReviewListResponseDTO.builder()
                    .reviews(myReviewListDTOS)
                    .reviewCount(myReviewListDTOS != null ? myReviewListDTOS.size() : 0)
                    .name(memberName)
                    .build();
        }
    }
}
