package dev.admin.voucher.repository;

import dev.admin.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, VoucherCustomRepository {
    @Modifying
    @Query("DELETE FROM VoucherOwnership vo WHERE vo.voucher.id = :voucherId")
    void deleteVoucherOwnershipsByVoucherId(@Param("voucherId") Long voucherId);
}
