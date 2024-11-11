package umc.spring.service.regionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.repository.RegionRepository;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public boolean isRegion(String value) {
        return regionRepository.existsByName(value);
    }
}
