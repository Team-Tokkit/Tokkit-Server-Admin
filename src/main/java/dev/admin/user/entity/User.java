package dev.admin.user.entity;


import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Column(nullable = false)
    private String simplePassword;

    private Boolean isDormant;

    private String roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Wallet wallet;
    public void update(String name, String simplePassword, String phoneNumber) {
        this.name = name;
        this.simplePassword = simplePassword;
        this.phoneNumber = phoneNumber;
    }
    public void changeStatus(boolean status) {
        this.isDormant = status;
    }
}