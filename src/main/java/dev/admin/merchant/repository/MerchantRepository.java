package dev.admin.merchant.repository;

import dev.admin.merchant.dto.response.MerchantSimpleResponseDto;
import dev.admin.merchant.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query("""
        SELECT new dev.admin.merchant.dto.response.MerchantSimpleResponseDto(
            m.id,
            m.name,
            m.email,
            m.phoneNumber,
            m.isDormant,
            m.createdAt
        )
        FROM Merchant m
        WHERE (:keyword IS NULL 
            OR m.name LIKE CONCAT('%', :keyword, '%') 
            OR m.email LIKE CONCAT('%', :keyword, '%'))
    """)
    Page<MerchantSimpleResponseDto> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
