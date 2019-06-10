package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reference_object")
public class ReferenceObject extends BaseEntity {

    public ReferenceObject(String name, Dimension dimension, String ref, boolean is_time) {
        this.name = name;
        this.dimension = dimension;
        this.ref = ref;
        this.is_time = is_time;
    }

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @Column(name = "ref")
    private String ref;

    @Column(name = "is_time")
    private boolean is_time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<ReferenceObjectCombination> referenceObjectCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<ReferenceObjectHierarchy> referenceObjectHierarchies = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceObject")
    private Set<Fact> facts = new HashSet<>();

}
