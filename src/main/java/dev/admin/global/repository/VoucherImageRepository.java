package dev.admin.global.repository;

import dev.admin.global.entity.VoucherImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VoucherImageRepository extends JpaRepository<VoucherImage, Long> {
}
