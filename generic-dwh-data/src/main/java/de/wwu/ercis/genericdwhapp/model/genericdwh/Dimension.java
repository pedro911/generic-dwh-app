package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table (name = "dimension")
public class Dimension extends BaseEntity {

    @NonNull
    @Column (name = "name")
    private String name;

    @NonNull
    @Column(name = "is_time")
    private boolean is_time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<DimensionCombination> dimensionCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<DimensionHierarchy> dimensionHierarchies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dimension")
    private Set<ReferenceObject> referenceObjects = new HashSet<>();
}