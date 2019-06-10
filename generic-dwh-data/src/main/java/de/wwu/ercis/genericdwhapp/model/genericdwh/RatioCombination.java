package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratio_combination")
public class RatioCombination extends BaseEntity {

    @ManyToOne
    private Ratio combination;

    @ManyToOne
    private Ratio subordinate;

    @Column(name = "algorithm")
    private String algorithm;
}
