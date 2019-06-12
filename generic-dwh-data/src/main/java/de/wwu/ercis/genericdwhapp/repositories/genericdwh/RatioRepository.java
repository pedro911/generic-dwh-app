package de.wwu.ercis.genericdwhapp.repositories.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatioRepository extends JpaRepository<Ratio, Long> {

    List<Ratio> findByOrderByIdAsc();


}
