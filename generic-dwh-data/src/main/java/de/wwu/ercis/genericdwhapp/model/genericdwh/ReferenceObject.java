package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "reference_object")
public class ReferenceObject extends BaseEntity {

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @OneToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @NonNull
    @Column(name = "ref")
    private String ref;

    @NonNull
    @Column(name = "is_time")
    private boolean is_time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<ReferenceObjectCombination> referenceObjectCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<ReferenceObjectHierarchy> referenceObjectHierarchies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceObject")
    private Set<Fact> facts = new HashSet<>();

}
