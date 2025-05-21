package dev.admin.store.repository;

import dev.admin.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    // 스토어 전체 조회
    Page<Store> findAll(Pageable pageable);
}
