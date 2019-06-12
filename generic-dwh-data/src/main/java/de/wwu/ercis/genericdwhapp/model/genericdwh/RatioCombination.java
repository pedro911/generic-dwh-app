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
@IdClass(RatioCombinationPK.class)
@Table(name = "ratio_combination")
public class RatioCombination {

    @Id
    @Column(name = "combination_id", nullable = false)
    private Long combinationId;

    @Id
    @Column(name = "subordinate_id", nullable = false)
    private Long subordinateId;

    @ManyToOne
    @JoinColumn(name = "combination_id", referencedColumnName = "id", insertable=false , updatable=false)
    private Ratio combination;

    @ManyToOne
    @JoinColumn(name = "subordinate_id", referencedColumnName = "id", insertable=false , updatable=false)
    private Ratio subordinate;

    @Column(name = "algorithm")
    private String algorithm;

    public RatioCombination(Long combination_id, Long subordinate_id, String algorithm) {
        this.combinationId = combination_id;
        this.subordinateId = subordinate_id;
        this.algorithm = algorithm;
    }

    public RatioCombination(Long combination_id, Long subordinate_id) {
        this.combinationId = combination_id;
        this.subordinateId = subordinate_id;
    }
}
