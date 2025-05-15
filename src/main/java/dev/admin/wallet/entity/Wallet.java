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

    public void updateBalance(Long deposit, Long token) {
        this.depositBalance = deposit;
        this.tokenBalance = token;
    }

    /**
     * Wallet의 주인이 주인일수도 있고 가맹점주일수도 있기 때문에, null 이거나 둘 다의 값이 들어가는 경우를 방지 (유효성 검증)
     */
    @PostPersist
    @PostLoad
    private void validateWalletOwnership() {
        boolean userExists = user != null;
        boolean merchantExists = merchant != null;

        if ((userExists && merchantExists) || (!userExists && !merchantExists)) {
            throw new IllegalStateException("지갑은 사용자 또는 가맹점 중 하나에만 귀속되어야 합니다.");
        }
    }
}
