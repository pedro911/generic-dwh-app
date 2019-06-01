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
@Table(name = "dimension_hierarchy")
public class DimensionHierarchy extends BaseEntity {

    @ManyToOne
    private Dimension parent;

    @ManyToOne
    private Dimension child;
}
