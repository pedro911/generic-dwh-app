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
@Table(name = "ratio")
public class Ratio extends BaseEntity {

    public Ratio(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<RatioCombination> ratioCombinationsByCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subordinate")
    private Set<RatioCombination> ratioCombinationsBySubordinates = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratio")
    private Set<Fact> facts = new HashSet<>();

}
