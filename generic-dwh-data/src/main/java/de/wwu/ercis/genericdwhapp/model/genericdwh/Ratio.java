package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ratio")
public class Ratio extends BaseEntity {

    @Column(name = "name")
    @NonNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "combination")
    private Set<RatioCombination> ratioCombinations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratio")
    private Set<Fact> facts = new HashSet<>();

}
