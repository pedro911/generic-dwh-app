package de.wwu.ercis.genericdwhapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table (name = "dimension")
public class Dimension extends BaseEntity {

    @Column (name = "name")
    private String name;

}
