package de.wwu.ercis.genericdwhapp.repositories.standard;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
}
