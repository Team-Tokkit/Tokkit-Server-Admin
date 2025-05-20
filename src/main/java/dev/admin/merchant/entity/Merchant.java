package dev.admin.merchant.entity;

import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.store.entity.Store;
import dev.admin.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Merchant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String simplePassword;

    @Column(nullable = false)
    private String businessNumber;

    private Boolean isDormant;

    private String roles;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "merchant")
    private Wallet wallet;

    @OneToOne(mappedBy = "merchant")
    private Store store;

    public void update(String name, String simplePassword , String phoneNumber) {
        this.name = name;
        this.simplePassword = simplePassword;
        this.phoneNumber = phoneNumber;
    }

    public void changeStatus(boolean isDormant) {
        this.isDormant = isDormant;
    }
}
