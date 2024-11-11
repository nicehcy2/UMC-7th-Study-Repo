package umc.spring.service.storeService;

import umc.spring.dto.StoreRequestDTO;

public interface StoreService {
    Boolean addStore(StoreRequestDTO.JoinDTO request);
    Boolean isStore(String value);
}
