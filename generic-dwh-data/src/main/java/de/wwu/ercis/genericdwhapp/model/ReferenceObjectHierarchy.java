package de.wwu.ercis.genericdwhapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "reference_object_hierarchy")
public class ReferenceObjectHierarchy extends BaseEntity {


    @ManyToOne
    private ReferenceObject parent;

    @ManyToOne
    private ReferenceObject child;

}
