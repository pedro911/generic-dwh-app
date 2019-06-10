package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reference_object_hierarchy")
public class ReferenceObjectHierarchy extends BaseEntity {

    @ManyToOne
    private ReferenceObject parent;

    @ManyToOne
    private ReferenceObject child;

}
