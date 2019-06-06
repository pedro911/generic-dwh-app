package de.wwu.ercis.genericdwhapp.repositories.standard;

import de.wwu.ercis.genericdwhapp.model.standard.NationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<NationEntity, Long> {
}
