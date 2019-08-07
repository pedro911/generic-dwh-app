package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dim_customer")
public class Customer {

    @Id
    @Column(name = "PK_CUSTKEY", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Fact> facts = new ArrayList<>();

    @Basic
    @Column(name = "C_MKTSEGMENT", nullable = true, length = 10)
    private String mktsegment;

    @Basic
    @Column(name = "C_NAME", nullable = true, length = 25)
    private String name;

    @Basic
    @Column(name = "C_ADDRESS", nullable = true, length = 40)
    private String address;

    @Basic
    @Column(name = "C_PHONE", nullable = true, length = 15)
    private String phone;

    @Basic
    @Column(name = "C_COMMENT", nullable = true, length = 118)
    private String comment;

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
