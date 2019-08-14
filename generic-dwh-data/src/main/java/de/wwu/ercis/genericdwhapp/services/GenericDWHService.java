package de.wwu.ercis.genericdwhapp.services;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface GenericDWHService<T, ID> {

    List<T> findAll();

    List<T> findAllSort(Sort sort);

    T findById(ID id);

    T save(T object);

}
