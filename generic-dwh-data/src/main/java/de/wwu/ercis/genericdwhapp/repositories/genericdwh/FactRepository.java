package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FactRepository extends JpaRepository<Fact, Long> {

    List<Fact> findByOrderByRatioIdAsc();

    List<Fact> findAllByRatioIdAndAndReferenceObjectId(Long ratioId, Long referenceObjectID);

    Optional<Fact> findByReferenceObjectAndRatio(ReferenceObject referenceObject, Ratio ratio);


}
