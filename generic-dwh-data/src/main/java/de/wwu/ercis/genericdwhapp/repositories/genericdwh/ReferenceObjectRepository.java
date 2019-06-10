package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceObjectRepository extends JpaRepository<ReferenceObject, Long> {
}
