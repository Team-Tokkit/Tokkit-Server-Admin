package dev.admin.wallet.entity;


import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.merchant.entity.Merchant;
import dev.admin.user.entity.User;
import dev.admin.wallet.enums.WalletType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Wallet extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Long depositBalance;

    private Long tokenBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletType walletType;

}
