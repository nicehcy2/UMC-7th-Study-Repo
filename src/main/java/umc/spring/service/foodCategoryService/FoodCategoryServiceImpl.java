package umc.spring.service.foodCategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.repository.FoodCategoryRepository;

@Service
@RequiredArgsConstructor
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public boolean isFoodCategory(Long value) {
        return foodCategoryRepository.existsById(value);
    }
}
