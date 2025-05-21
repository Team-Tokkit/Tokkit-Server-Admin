package dev.admin.voucher.repository;

import dev.admin.global.entity.VoucherStore;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.entity.Store;
import dev.admin.voucher.dto.request.VoucherSearchRequest;
import dev.admin.voucher.entity.Voucher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class VoucherCustomRepositoryImpl implements VoucherCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Voucher> searchVouchers(VoucherSearchRequest request, Pageable pageable) {
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(v) FROM Voucher v WHERE 1=1");
        StringBuilder jpql = new StringBuilder("SELECT v FROM Voucher v LEFT JOIN FETCH v.image WHERE 1=1");

        if (request.storeCategory() != null) {
            countJpql.append(" AND v.storeCategory = :category");
            jpql.append(" AND v.storeCategory = :category");
        }

        if (StringUtils.hasText(request.searchKeyword())) {
            countJpql.append(" AND LOWER(v.name) LIKE LOWER(CONCAT('%', :keyword, '%'))");
            jpql.append(" AND LOWER(v.name) LIKE LOWER(CONCAT('%', :keyword, '%'))");
        }

        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
        TypedQuery<Voucher> query = em.createQuery(jpql.toString(), Voucher.class);

        if (request.storeCategory() != null) {
            countQuery.setParameter("category", request.storeCategory());
            query.setParameter("category", request.storeCategory());
        }

        if (StringUtils.hasText(request.searchKeyword())) {
            countQuery.setParameter("keyword", request.searchKeyword());
            query.setParameter("keyword", request.searchKeyword());
        }

        long total = countQuery.getSingleResult();

        List<Voucher> result = query
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public Page<StoreListResponseDto> findStoresByVoucherId(Long voucherId, Pageable pageable) {
        // 1. 먼저 VoucherStore 기준으로 페이징
        TypedQuery<VoucherStore> query = em.createQuery(
                "SELECT vs FROM VoucherStore vs JOIN FETCH vs.store WHERE vs.voucher.id = :voucherId",
                VoucherStore.class
        );
        query.setParameter("voucherId", voucherId);

        List<VoucherStore> voucherStores = query
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // 2. Store 추출 후 DTO 매핑
        List<StoreListResponseDto> dtoList = voucherStores.stream()
                .map(vs -> StoreListResponseDto.from(vs.getStore()))
                .toList();

        // 3. Count 쿼리
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(vs) FROM VoucherStore vs WHERE vs.voucher.id = :voucherId",
                Long.class
        );
        countQuery.setParameter("voucherId", voucherId);
        long total = countQuery.getSingleResult();

        return new PageImpl<>(dtoList, pageable, total);
    }



}