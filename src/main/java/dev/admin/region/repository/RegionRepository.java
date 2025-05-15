package dev.admin.region.repository;

import dev.admin.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
