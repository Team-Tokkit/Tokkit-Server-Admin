package dev.admin.voucher_ownership.entity;

import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.voucher.entity.Voucher;
import dev.admin.voucher_ownership.enums.VoucherOwnershipStatus;
import dev.admin.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VoucherOwnership extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;


    private Long remainingAmount; // 남은 바우처 금액


    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherOwnershipStatus status = VoucherOwnershipStatus.AVAILABLE;

}
