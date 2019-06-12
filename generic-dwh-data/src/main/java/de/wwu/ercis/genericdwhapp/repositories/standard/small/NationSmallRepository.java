package de.wwu.ercis.genericdwhapp.repositories.standard.small;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NationSmallRepository extends JpaRepository<NationEntity, Long> {

    List<NationEntity> findAll(Sort sort);
}
