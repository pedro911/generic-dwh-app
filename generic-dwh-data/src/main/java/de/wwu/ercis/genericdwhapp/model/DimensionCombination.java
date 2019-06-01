package de.wwu.ercis.genericdwhapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "dimension_combination")
public class DimensionCombination extends BaseEntity{

    @ManyToOne
    private Dimension component;

    @ManyToOne
    private Dimension aggregate;

}
