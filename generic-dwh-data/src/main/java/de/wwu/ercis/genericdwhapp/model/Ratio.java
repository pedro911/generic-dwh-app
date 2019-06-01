package de.wwu.ercis.genericdwhapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "ratio")
public class Ratio extends BaseEntity {

    @Column(name = "name")
    private String name;

}
