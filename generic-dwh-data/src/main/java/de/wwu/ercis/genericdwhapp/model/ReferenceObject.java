package de.wwu.ercis.genericdwhapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reference_object")
public class ReferenceObject extends BaseEntity{

    @Column(name = "name")
    private String name;

    @OneToOne
    private Dimension dimension;

    @Column(name = "ref")
    private String ref;

    @Column(name = "is_time")
    private boolean is_time;

}
