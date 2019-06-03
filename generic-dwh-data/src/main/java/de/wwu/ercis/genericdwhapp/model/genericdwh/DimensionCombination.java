package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dimension_combination")
public class DimensionCombination extends BaseEntity {

    @NonNull
    @ManyToOne
    private Dimension combination;

    @NonNull
    @ManyToOne
    private Dimension subordinate;

}
