package dev.admin.transaction.repository;

import dev.admin.transaction.entity.Transaction;
import dev.admin.transaction.enums.TransactionStatus;
import dev.admin.transaction.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
    SELECT t FROM Transaction t
    WHERE (:type IS NULL OR t.type = :type)
      AND (:status IS NULL OR t.status = :status)
      AND (:walletId IS NULL OR t.wallet.id = :walletId)
    """)
    Page<Transaction> searchByCondition(@Param("type") TransactionType type,
                                        @Param("status") TransactionStatus status,
                                        @Param("walletId") Long walletId,
                                        Pageable pageable);

}
