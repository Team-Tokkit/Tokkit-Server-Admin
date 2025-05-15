package dev.admin.merchant.repository;

import dev.admin.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
