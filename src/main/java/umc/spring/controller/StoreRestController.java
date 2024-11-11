package umc.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.dto.StoreRequestDTO;
import umc.spring.service.storeService.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreService storeService;

    @PostMapping
    ApiResponse<Boolean> addStore(@RequestBody @Valid StoreRequestDTO.JoinDTO request) {
        return ApiResponse.onSuccess(storeService.addStore(request));
    }
}
