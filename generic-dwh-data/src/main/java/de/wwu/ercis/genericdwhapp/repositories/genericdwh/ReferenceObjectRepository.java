package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReferenceObjectRepository extends JpaRepository<ReferenceObject, Long> {

    List<ReferenceObject> findByOrderByIdAsc();

    List<ReferenceObject> findAllByDimensionIn(Dimension dimension);

    List<ReferenceObject> findFirst1000ByDimensionIn(Dimension dimension);

    List<ReferenceObject> findAllByNameContaining(String name);

    List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name);

    Optional<ReferenceObject> findFirstByDimension(Dimension dimension);

}
