package de.wwu.ercis.genericdwhapp.repositories.starschema;

import de.wwu.ercis.genericdwhapp.model.starschema.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
