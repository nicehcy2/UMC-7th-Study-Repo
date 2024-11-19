package umc.spring.service.storeService;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.dto.StoreRequestDTO;

public interface StoreService {
    Boolean addStore(StoreRequestDTO.JoinDTO request);
    Boolean isStore(String value);
    Page<Review> getReviewList(Long storeId, Integer page);
    Page<Mission> getMissionList(Long storeId, Integer page);
}
