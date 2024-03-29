package de.wwu.ercis.genericdwhapp.repositories.tpchstandard;

import de.wwu.ercis.genericdwhapp.model.tpchstandard.NationEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface NationRepository extends JpaRepository<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);
}
