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
@Table(name = "dim_part")
public class Part {

    @Id
    @Column(name = "PK_PARTKEY", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "part")
    private List<Fact> facts = new ArrayList<>();

    @Basic
    @Column(name = "P_NAME", nullable = true, length = 55)
    private String name;

    @Basic
    @Column(name = "P_TYPE", nullable = true, length = 25)
    private String type;

    @Basic
    @Column(name = "P_SIZE", nullable = true)
    private Integer size;

    @Basic
    @Column(name = "P_BRAND", nullable = true, length = 10)
    private String brand;

    @Basic
    @Column(name = "P_CONTAINER", nullable = true, length = 10)
    private String container;

    @Basic
    @Column(name = "P_MFGR", nullable = true, length = 25)
    private String manufacturerGroup;

    @Basic
    @Column(name = "P_COMMENT", nullable = true, length = 23)
    private String comment;

}
