package de.wwu.ercis.genericdwhapp.model.genericdwh;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@IdClass(DimensionHierarchyPK.class)
@Table(name = "dimension_hierarchy")
public class DimensionHierarchy{

    @Id
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Id
    @Column(name = "child_id", nullable = false)
    private Long childId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable=false , updatable=false)
    @JsonIgnore
    private Dimension parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", referencedColumnName = "id", insertable=false , updatable=false)
    @JsonIgnore
    private Dimension child;

    public DimensionHierarchy(Long parent_id, Long child_id) {
        this.parentId = parent_id;
        this.childId = child_id;
    }
}
