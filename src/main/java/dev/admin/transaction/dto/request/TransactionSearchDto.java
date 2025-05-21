package dev.admin.transaction.dto.request;

import dev.admin.transaction.enums.TransactionStatus;
import dev.admin.transaction.enums.TransactionType;

public record TransactionSearchDto(
        TransactionType type,
        TransactionStatus status,
        Long walletId
) {}
