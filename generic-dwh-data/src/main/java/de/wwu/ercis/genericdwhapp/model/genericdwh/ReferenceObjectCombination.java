package de.wwu.ercis.genericdwhapp.model.genericdwh;

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
@IdClass(ReferenceObjectCombinationPK.class)
@Table(name = "reference_object_combination")
public class ReferenceObjectCombination {

    @Id
    @Column(name = "combination_id", nullable = false)
    private Long combinationId;

    @Id
    @Column(name = "subordinate_id", nullable = false)
    private Long subordinateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combination_id", referencedColumnName = "id", insertable=false , updatable=false)
    private ReferenceObject combination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subordinate_id", referencedColumnName = "id", insertable=false , updatable=false)
    private ReferenceObject subordinate;

    public ReferenceObjectCombination(Long combinationId, Long subordinate_id) {
        this.combinationId = combinationId;
        this.subordinateId = subordinate_id;
    }
}
