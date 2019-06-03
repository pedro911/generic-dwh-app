package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "dimension_hierarchy")
public class DimensionHierarchy extends BaseEntity {

    @NonNull
    @ManyToOne
    private Dimension parent;

    @NonNull
    @ManyToOne
    private Dimension child;
}
