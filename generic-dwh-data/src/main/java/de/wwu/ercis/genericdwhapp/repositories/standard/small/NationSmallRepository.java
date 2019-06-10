package de.wwu.ercis.genericdwhapp.repositories.standard.small;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationSmallRepository extends JpaRepository<NationEntity, Long> {
}
