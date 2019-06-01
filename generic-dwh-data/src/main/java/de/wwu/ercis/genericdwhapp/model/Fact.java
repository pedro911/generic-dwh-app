package de.wwu.ercis.genericdwhapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "fact")
public class Fact extends BaseEntity {

    @ManyToOne
    private ReferenceObject referenceObject;

    @ManyToOne
    private Ratio ratio;

    @Column(name = "value")
    private double value;

}
