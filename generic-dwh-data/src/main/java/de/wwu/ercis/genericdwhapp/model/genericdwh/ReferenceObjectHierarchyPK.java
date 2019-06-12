package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ReferenceObjectHierarchyPK implements Serializable {

    @Id
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Id
    @Column(name = "child_id", nullable = false)
    private Long childId;

}
