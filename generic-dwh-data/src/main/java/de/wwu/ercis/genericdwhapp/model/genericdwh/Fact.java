package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fact")
public class Fact extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "reference_object_id")
    private ReferenceObject referenceObject;

    @ManyToOne
    @JoinColumn(name = "ratio_id")
    private Ratio ratio;

    @Column(name = "value")
    private double value;

}
