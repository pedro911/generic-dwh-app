package de.wwu.ercis.genericdwhapp.repositories.standard;

import de.wwu.ercis.genericdwhapp.model.standard.RegionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    List<RegionEntity> findAll(Sort sort);
}
