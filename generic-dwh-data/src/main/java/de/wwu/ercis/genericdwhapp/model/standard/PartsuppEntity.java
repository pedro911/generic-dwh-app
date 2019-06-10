package de.wwu.ercis.genericdwhapp.model.standard;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "partsupp", schema = "tpch_std_small")
public class PartsuppEntity {

    @EmbeddedId
    private PartsuppEntityPK partsuppEntityPK;

    @Basic
    @Column(name = "ps_supplycost", nullable = false, precision = 0)
    private double psSupplycost;

    @Basic
    @Column(name = "ps_availqty", nullable = true, precision = 0)
    private Double psAvailqty;

    @Basic
    @Column(name = "ps_comment", nullable = true, length = 199)
    private String psComment;

    @OneToMany(mappedBy = "partsupp")
    private Set<LineitemEntity> lineitems;

    @ManyToOne
    @JoinColumn(name = "ps_partkey", referencedColumnName = "p_partkey", nullable = false,
            insertable=false, updatable=false)
    private PartEntity partByPsPartkey;

    @ManyToOne
    @JoinColumn(name = "ps_suppkey", referencedColumnName = "s_suppkey", nullable = false,
            insertable=false, updatable=false)
    private SupplierEntity supplierByPsSuppkey;

}
