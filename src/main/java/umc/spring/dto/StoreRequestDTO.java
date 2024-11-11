package umc.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import umc.spring.validation.annotation.ExistRegion;

public class StoreRequestDTO {

    @Getter
    public static class JoinDTO {

        @NotBlank
        private String name;
        @NotBlank
        private String address;
        @Min(value = 0)
        private Float score;
        @ExistRegion
        private String region;
    }
}
