package dev.admin.store.repository;

import dev.admin.store.dto.request.StoreSearchRequestDto;
import dev.admin.store.dto.response.StoreListResponseDto;
import dev.admin.store.entity.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import java.util.List;

public class StoreCustomRepositoryImpl implements StoreCustomRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<StoreListResponseDto> searchStores(StoreSearchRequestDto request, Pageable pageable) {
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(s) FROM Store s JOIN s.region r WHERE 1=1");
        StringBuilder jpql = new StringBuilder("SELECT s FROM Store s JOIN s.region r WHERE 1=1");

        if (StringUtils.hasText(request.sidoName()) && !"all".equalsIgnoreCase(request.sidoName())) {
            countJpql.append(" AND r.sidoName = :sidoName");
            jpql.append(" AND r.sidoName = :sidoName");
        }

        if (StringUtils.hasText(request.sigunguName()) && !"all".equalsIgnoreCase(request.sigunguName())) {
            countJpql.append(" AND r.sigunguName = :sigunguName");
            jpql.append(" AND r.sigunguName = :sigunguName");
        }

        if (request.storeCategory() != null && !"all".equalsIgnoreCase(request.storeCategory().name())) {
            countJpql.append(" AND s.storeCategory = :storeCategory");
            jpql.append(" AND s.storeCategory = :storeCategory");
        }

        if (StringUtils.hasText(request.keyword())) {
            countJpql.append(" AND (LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.roadAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))");
            jpql.append(" AND (LOWER(s.storeName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.roadAddress) LIKE LOWER(CONCAT('%', :keyword, '%')))");
        }

        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
        TypedQuery<Store> query = em.createQuery(jpql.toString(), Store.class);

        if (StringUtils.hasText(request.sidoName()) && !"all".equalsIgnoreCase(request.sidoName())) {
            countQuery.setParameter("sidoName", request.sidoName());
            query.setParameter("sidoName", request.sidoName());
        }

        if (StringUtils.hasText(request.sigunguName()) && !"all".equalsIgnoreCase(request.sigunguName())) {
            countQuery.setParameter("sigunguName", request.sigunguName());
            query.setParameter("sigunguName", request.sigunguName());
        }

        if (request.storeCategory() != null && !"all".equalsIgnoreCase(request.storeCategory().name())) {
            countQuery.setParameter("storeCategory", request.storeCategory());
            query.setParameter("storeCategory", request.storeCategory());
        }

        if (StringUtils.hasText(request.keyword())) {
            countQuery.setParameter("keyword", request.keyword());
            query.setParameter("keyword", request.keyword());
        }

        long total = countQuery.getSingleResult();

        List<Store> stores = query
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<StoreListResponseDto> dtoList = stores.stream()
                .map(StoreListResponseDto::from)
                .toList();

        return new PageImpl<>(dtoList, pageable, total);
    }

}
