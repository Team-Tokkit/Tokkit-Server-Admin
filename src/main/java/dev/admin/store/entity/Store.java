package dev.admin.store.entity;

import dev.admin.global.entity.BaseTimeEntity;
import dev.admin.merchant.entity.Merchant;
import dev.admin.region.entity.Region;
import dev.admin.store.enums.StoreCategory;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;

    private String roadAddress;

    private String newZipcode;

    private Double longitude;

    private Double latitude;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", unique = true)
    private Merchant merchant;

}

