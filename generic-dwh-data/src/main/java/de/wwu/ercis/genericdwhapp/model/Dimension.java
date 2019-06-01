package de.wwu.ercis.genericdwhapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "dimension")
public class Dimension extends BaseEntity {

    @Column (name = "name")
    private String name;

    @Column(name = "is_time")
    private boolean is_time;

}
