package de.wwu.ercis.genericdwhapp.model.tpchstandard;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lineitem",  schema = "tpch_std_small")
public class LineitemEntity {

    @EmbeddedId
    private LineitemEntityPK lineitemEntityPK;

    @Basic
    @Column(name = "l_shipdate", nullable = true)
    private Timestamp lShipdate;

    @Basic
    @Column(name = "l_discount", nullable = false, precision = 0)
    private Double lDiscount;

    @Basic
    @Column(name = "l_extendedprice", nullable = false, precision = 0)
    private Double lExtendedprice;

    @Basic
    @Column(name = "l_quantity", nullable = false, precision = 0)
    private BigInteger lQuantity;

    @Basic
    @Column(name = "l_returnflag", nullable = true)
    private String lReturnflag;

    @Basic
    @Column(name = "l_linestatus", nullable = true)
    private String lLinestatus;

    @Basic
    @Column(name = "l_tax", nullable = false, precision = 0)
    private BigInteger lTax;

    @Basic
    @Column(name = "l_commitdate", nullable = true)
    private Timestamp lCommitdate;

    @Basic
    @Column(name = "l_receiptdate", nullable = true)
    private Timestamp lReceiptdate;

    @Basic
    @Column(name = "l_shipmode", nullable = true, length = 10)
    private String lShipmode;

    @Basic
    @Column(name = "l_shipinstruct", nullable = true, length = 25)
    private String lShipinstruct;

    @Basic
    @Column(name = "l_comment", nullable = true, length = 44)
    private String lComment;

    @ManyToOne
    @JoinColumn(name = "l_orderkey", referencedColumnName = "o_orderkey", nullable = false,
            insertable=false, updatable=false)
    private OrdersEntity ordersByLOrderkey;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "l_partkey", referencedColumnName = "ps_partkey", nullable = false),
            @JoinColumn(name = "l_suppkey", referencedColumnName = "ps_suppkey", nullable = false)})
    private PartsuppEntity partsupp;

}
