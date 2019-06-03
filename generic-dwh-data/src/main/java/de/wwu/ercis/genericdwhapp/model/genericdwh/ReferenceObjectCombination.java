package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "reference_object_combination")
public class ReferenceObjectCombination extends BaseEntity {

    @NonNull
    @ManyToOne
    private ReferenceObject combination;

    @NonNull
    @ManyToOne
    private ReferenceObject subordinate;

}
