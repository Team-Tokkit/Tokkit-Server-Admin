package dev.admin.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.admin.wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
