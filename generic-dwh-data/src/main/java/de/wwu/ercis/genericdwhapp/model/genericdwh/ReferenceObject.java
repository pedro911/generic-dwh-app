package de.wwu.ercis.genericdwhapp.model.genericdwh;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @Column(name = "ref")
    private String ref;

    @Column(name = "is_time", columnDefinition = "tinyint(1)")
    private boolean is_time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination", fetch = FetchType.LAZY)
    private Set<ReferenceObjectCombination> referenceObjectCombinationsByCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subordinate", fetch = FetchType.LAZY)
    private Set<ReferenceObjectCombination> referenceObjectCombinationsBySubordinates = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<ReferenceObjectHierarchy> referenceObjectHierarchiesByParents = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child", fetch = FetchType.LAZY)
    private Set<ReferenceObjectHierarchy> referenceObjectHierarchiesByChildren = new HashSet<>();

}
