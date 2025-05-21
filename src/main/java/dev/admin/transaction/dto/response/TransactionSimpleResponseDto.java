package dev.admin.transaction.dto.response;

import dev.admin.transaction.entity.Transaction;
import dev.admin.transaction.enums.TransactionStatus;
import dev.admin.transaction.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionSimpleResponseDto(
        Long id,
        String txHash,
        TransactionType type,
        TransactionStatus status,
        Long amount,
        LocalDateTime createdAt
) {
    public static TransactionSimpleResponseDto from(Transaction tx) {
        return new TransactionSimpleResponseDto(
                tx.getId(),
                tx.getTxHash(),
                tx.getType(),
                tx.getStatus(),
                tx.getAmount(),
                tx.getCreatedAt()
        );
    }
}
