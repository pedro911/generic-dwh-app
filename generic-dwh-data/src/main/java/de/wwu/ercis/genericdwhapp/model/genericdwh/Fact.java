package de.wwu.ercis.genericdwhapp.model.genericdwh;

import de.wwu.ercis.genericdwhapp.model.BaseEntity;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@Table(name = "fact")
public class Fact extends BaseEntity {

    @NonNull
    @ManyToOne
    @JoinColumn(name = "reference_object_id")
    private ReferenceObject referenceObject;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "ratio_id")
    private Ratio ratio;

    @NonNull
    @Column(name = "value")
    private double value;

}
