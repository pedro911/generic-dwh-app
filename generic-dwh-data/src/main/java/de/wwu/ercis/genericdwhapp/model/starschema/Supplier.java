package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "dim_supplier")
public class Supplier {

    @Id
    @Column(name = "PK_SUPPLIER", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    private List<StarSchemaFact> starSchemaFacts = new ArrayList<>();

    @Basic
    @Column(name = "S_COMMENT", nullable = true, length = 102)
    private String comment;

    @Basic
    @Column(name = "S_NAME", nullable = true, length = 25)
    private String name;

    @Basic
    @Column(name = "S_ADDRESS", nullable = true, length = 40)
    private String address;

    @Basic
    @Column(name = "S_PHONE", nullable = true, length = 15)
    private String phone;

    @Basic
    @Column(name = "N_NAME", nullable = true, length = 25)
    private String nation;

    @Basic
    @Column(name = "N_COMMENT", nullable = true, length = 152)
    private String nationComment;

    @Basic
    @Column(name = "R_NAME", nullable = true, length = 25)
    private String region;

    @Basic
    @Column(name = "R_COMMENT", nullable = true, length = 152)
    private String regionComment;

}
