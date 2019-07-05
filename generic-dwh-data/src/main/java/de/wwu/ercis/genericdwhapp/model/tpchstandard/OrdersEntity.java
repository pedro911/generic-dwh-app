package de.wwu.ercis.genericdwhapp.model.tpchstandard;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "orders", schema = "tpch_std_small")
public class OrdersEntity {

    @Id
    @Column(name = "o_orderkey", nullable = false, precision = 0)
    private Long oOrderkey;

    @Basic
    @Column(name = "o_orderdate", nullable = true)
    private Timestamp oOrderdate;

    @Basic
    @Column(name = "o_orderpriority", nullable = true, length = 15)
    private String oOrderpriority;

    @Basic
    @Column(name = "o_shippriority", nullable = true, precision = 0)
    private BigInteger oShippriority;

    @Basic
    @Column(name = "o_clerk", nullable = true, length = 15)
    private String oClerk;

    @Basic
    @Column(name = "o_orderstatus", nullable = true)
    private String oOrderstatus;

    @Basic
    @Column(name = "o_totalprice", nullable = true, precision = 0)
    private Double oTotalprice;

    @Basic
    @Column(name = "o_comment", nullable = true, length = 79)
    private String oComment;

    @OneToMany(mappedBy = "ordersByLOrderkey")
    private Set<LineitemEntity> lineitemsByOOrderkey;

    @ManyToOne
    @JoinColumn(name = "o_custkey", referencedColumnName = "c_custkey", nullable = false)
    private CustomerEntity customerByOCustkey;

}
