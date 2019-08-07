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
@IdClass(LineorderPK.class)
@Table(name = "dim_lineorder")
public class Lineorder {

    @Id
    @Column(name = "O_ORDERKEY", nullable = false)
    private int orderKey;

    @Id
    @Column(name = "L_LINENUMBER", nullable = false)
    private int lineNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lineorder")
    private List<Fact> facts = new ArrayList<>();

    @Basic
    @Column(name = "O_CUSTKEY", nullable = true)
    private Integer customerKey;

    @Basic
    @Column(name = "O_ORDERPRIORITY", nullable = true, length = 15)
    private String orderPriority;

    @Basic
    @Column(name = "O_SHIPPRIORITY", nullable = true)
    private Integer orderShipPriority;

    @Basic
    @Column(name = "O_CLERK", nullable = true, length = 15)
    private String clerk;

    @Basic
    @Column(name = "O_ORDERSTATUS", nullable = true, length = 1)
    private String orderStatus;

    @Basic
    @Column(name = "O_COMMENT", nullable = true, length = 79)
    private String orderComment;

    @Basic
    @Column(name = "L_SUPPKEY", nullable = true)
    private Integer lineItemSupplierKey;

    @Basic
    @Column(name = "L_RETURNFLAG", nullable = true, length = 1)
    private String lineItemReturnFlag;

    @Basic
    @Column(name = "L_PARTKEY", nullable = true)
    private Integer lineItemPartKey;

    @Basic
    @Column(name = "L_LINESTATUS", nullable = true, length = 1)
    private String lineItemStatus;

    @Basic
    @Column(name = "L_SHIPMODE", nullable = true, length = 10)
    private String lineItemShipMode;

    @Basic
    @Column(name = "L_SHIPINSTRUCT", nullable = true, length = 25)
    private String lineItemShipInstruct;

    @Basic
    @Column(name = "L_COMMENT", nullable = true, length = 44)
    private String lineItemComment;

}
