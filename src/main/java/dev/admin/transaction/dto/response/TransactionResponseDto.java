package dev.admin.transaction.dto.response;

import dev.admin.transaction.entity.Transaction;
import dev.admin.transaction.enums.TransactionStatus;
import dev.admin.transaction.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponseDto(
        Long id,
        Long amount,
        String txHash,
        String description,
        TransactionStatus status,
        TransactionType type,
        String traceId,
        String failureReason,
        Long walletId,
        LocalDateTime createdAt
) {
    public static TransactionResponseDto from(Transaction tx) {
        return new TransactionResponseDto(
                tx.getId(),
                tx.getAmount(),
                tx.getTxHash(),
                tx.getDescription(),
                tx.getStatus(),
                tx.getType(),
                tx.getTraceId(),
                tx.getFailureReason(),
                tx.getWallet() != null ? tx.getWallet().getId() : null,
                tx.getCreatedAt()
        );
    }
}
