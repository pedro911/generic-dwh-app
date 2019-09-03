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
@IdClass(DimensionCombinationPK.class)
@Table(name = "dimension_combination")
public class DimensionCombination {

    @Id
    @Column(name = "combination_id", nullable = false)
    private Long combinationId;

    @Id
    @Column(name = "subordinate_id", nullable = false)
    private Long subordinateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combination_id", referencedColumnName = "id", insertable=false , updatable=false)
    private Dimension combination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subordinate_id", referencedColumnName = "id", insertable=false , updatable=false)
    private Dimension subordinate;

    @Column(name = "show_on", columnDefinition = "tinyint(1)", nullable = false)
    private boolean showOn;

    public DimensionCombination(Long combination_id, Long subordinate_id, boolean showOn) {
        this.combinationId = combination_id;
        this.subordinateId = subordinate_id;
        this.showOn = showOn;
    }
}
