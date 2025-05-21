package dev.admin.transaction.service.query;

import dev.admin.transaction.dto.request.TransactionSearchDto;
import dev.admin.transaction.dto.response.TransactionResponseDto;
import dev.admin.transaction.dto.response.TransactionSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionQueryService {
    TransactionResponseDto getById(Long id);

    Page<TransactionSimpleResponseDto> search(TransactionSearchDto condition, Pageable pageable);
}
