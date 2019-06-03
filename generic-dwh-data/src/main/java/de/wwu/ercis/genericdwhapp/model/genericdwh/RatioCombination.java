package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name = "ratio_combination")
public class RatioCombination extends BaseEntity {

    @NonNull
    @ManyToOne
    private Ratio combination;

    @NonNull
    @ManyToOne
    private Ratio subordinate;

    @Column(name = "algorithm")
    private String algorithm;
}
