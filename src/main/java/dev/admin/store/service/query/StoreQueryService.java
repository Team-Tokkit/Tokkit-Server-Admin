package dev.admin.store.service.query;

import dev.admin.store.dto.response.StoreListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreQueryService {
    // 전체 바우처 조회
    Page<StoreListResponseDto> getAllStores(Pageable pageable);
}
