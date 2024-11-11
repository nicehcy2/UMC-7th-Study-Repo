package umc.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import umc.spring.domain.Member;
import umc.spring.domain.Store;
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistStore;

public class ReviewRequestDTO {

    @Getter
    public static class JoinDTO {
        @NotBlank
        private String body;
        @Min(value = 0)
        private Float score;
        @ExistMember
        private String member;
        @ExistStore
        private String store;
    }
}
