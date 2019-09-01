package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@IdClass(FactPK.class)
@Table(name = "fact")
public class Fact {

    @Id
    @Column(name = "ratio_id", nullable = false)
    private Long ratioId;

    @Id
    @Column(name = "reference_object_id", nullable = false)
    private Long referenceObjectId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "reference_object_id", insertable=false , updatable=false)
    private ReferenceObject referenceObject;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ratio_id", insertable=false , updatable=false)
    private Ratio ratio;

    @Column(name = "value")
    private Double value;

    public Fact(Long ratioId, Long referenceObjectId, double value) {
        this.ratioId = ratioId;
        this.referenceObjectId = referenceObjectId;
        this.value = value;
    }
}
