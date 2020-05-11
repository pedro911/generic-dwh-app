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
@IdClass(ReferenceObjectHierarchyPK.class)
@Table(name = "reference_object_hierarchy")
public class ReferenceObjectHierarchy {

    @Id
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Id
    @Column(name = "child_id", nullable = false)
    private Long childId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable=false , updatable=false)
    private ReferenceObject parent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", referencedColumnName = "id", insertable=false , updatable=false)
    private ReferenceObject child;

    public ReferenceObjectHierarchy(Long parent_id, Long child_id) {
        this.parentId = parent_id;
        this.childId = child_id;
    }
}
