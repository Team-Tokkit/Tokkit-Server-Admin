package dev.admin.store.service.query;

import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.dto.request.StoreSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryService {
        Page<StoreListResponseDto> searchStores(StoreSearchRequestDto request, Pageable pageable);

}
