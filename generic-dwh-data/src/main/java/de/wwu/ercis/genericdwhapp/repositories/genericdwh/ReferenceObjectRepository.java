package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReferenceObjectRepository extends JpaRepository<ReferenceObject, Long>, ResultParser {

    List<ReferenceObject> findByOrderByIdAsc();

    List<ReferenceObject> findAllByDimensionIn(Dimension dimension);

    List<ReferenceObject> findAllByNameContaining(String name);

    List<ReferenceObject> findAllByDimensionInAndNameContaining(Dimension dimension, String name);



}
