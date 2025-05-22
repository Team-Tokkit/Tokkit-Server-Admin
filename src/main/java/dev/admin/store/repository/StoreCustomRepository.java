package dev.admin.store.repository;

import dev.admin.store.dto.request.StoreSearchRequestDto;
import dev.admin.store.dto.response.StoreListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreCustomRepository {
    Page<StoreListResponseDto> searchStores(StoreSearchRequestDto storeSearchRequestDto, Pageable pageable);
}
