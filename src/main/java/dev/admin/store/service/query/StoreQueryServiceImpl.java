package dev.admin.store.service.query;

import dev.admin.store.dto.request.StoreSearchRequestDto;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Page<StoreListResponseDto> searchStores(StoreSearchRequestDto request, Pageable pageable) {
        return storeRepository.searchStores(request, pageable);
    }
}
