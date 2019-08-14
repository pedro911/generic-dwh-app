package de.wwu.ercis.genericdwhapp.services.starchema.springdatajpa;

import de.wwu.ercis.genericdwhapp.model.starschema.Customer;
import de.wwu.ercis.genericdwhapp.repositories.starschema.CustomerRepository;
import de.wwu.ercis.genericdwhapp.services.starchema.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerSDJpaService implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerSDJpaService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll().forEach(customerList::add);
        return customerList;
    }

    @Override
    public List<Customer> findAllSort(Sort sort) {
        List<Customer> customerList = new ArrayList<>();
        customerRepository.findAll(sort).forEach(customerList::add);
        return customerList;
    }

    @Override
    public Customer findById(Long aLong) {
        return customerRepository.findById(aLong).orElse(null);
    }


}
