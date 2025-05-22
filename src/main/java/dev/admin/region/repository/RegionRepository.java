package dev.admin.region.repository;

import dev.admin.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findDistinctBySidoNameNotNull();
    List<Region> findBySidoName(String sidoName);
}
