package dev.admin.voucher.entity;

import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.global.entity.VoucherImage;
import dev.admin.global.entity.VoucherStore;
import dev.admin.merchant.entity.Merchant;
import dev.admin.store.enums.StoreCategory;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Voucher extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String detailDescription;

    // 할인금액
    @Column(nullable = false)
    private Integer price;

    // 정가
    @Column(nullable = false)
    private Integer originalPrice;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer remainingCount;

    @Column(nullable = false)
    private LocalDateTime validDate;

    @Column(nullable = false)
    private String refundPolicy;

    private String contact;

    @Builder.Default
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<VoucherStore> voucherStores = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id" , nullable = true)
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    @OneToOne(mappedBy = "voucher", cascade = CascadeType.ALL)
    private VoucherImage image;

    public void setImage(VoucherImage image) {
        this.image = image;
    }

}
