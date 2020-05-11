package de.wwu.ercis.genericdwhapp.model.genericdwh;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table (name = "dimension")
public class Dimension extends BaseEntity{

    public Dimension(String name, boolean is_time) {
        this.name = name;
        this.is_time = is_time;
    }

    @Column (name = "name")
    private String name;

    @Column(name = "is_time", columnDefinition = "tinyint(1)")
    private boolean is_time;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<DimensionCombination> dimensionCombinationsByCombinations = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subordinate")
    private Set<DimensionCombination> dimensionCombinationsBySubordinates = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private Set<DimensionHierarchy> dimensionHierarchiesByParents = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
    private Set<DimensionHierarchy> dimensionHierarchiesByChildren = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dimension")
    @JsonIgnore
    private Set<ReferenceObject> referenceObjects = new HashSet<>();
}