package dev.admin.transaction.service.query;

import dev.admin.global.apiPayload.code.status.ErrorStatus;
import dev.admin.global.apiPayload.exception.GeneralException;
import dev.admin.transaction.dto.request.TransactionSearchDto;
import dev.admin.transaction.dto.response.TransactionResponseDto;
import dev.admin.transaction.dto.response.TransactionSimpleResponseDto;
import dev.admin.transaction.entity.Transaction;
import dev.admin.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepository repository;

    @Override
    public TransactionResponseDto getById(Long id) {
        Transaction tx = repository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.TRANSACTION_NOT_FOUND));
        return TransactionResponseDto.from(tx);
    }

    @Override
    public Page<TransactionSimpleResponseDto> search(TransactionSearchDto condition, Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new GeneralException(ErrorStatus.INVALID_PAGE_NUMBER);
        }
        return repository.searchByCondition(
                condition.type(),
                condition.status(),
                condition.walletId(),
                pageable)
                .map(TransactionSimpleResponseDto::from);
    }
}
