package de.wwu.ercis.genericdwhapp.repositories.standard.small;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionSmallRepository extends JpaRepository<RegionEntity, Long> {
}
