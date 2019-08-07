package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fact")
public class Fact {

    @Id
    @Column(name = "PK_FACT", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CUSTOMER", insertable=false , updatable=false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="FK_ORDER", referencedColumnName="O_ORDERKEY"),
            @JoinColumn(name="L_LINENUMBER", referencedColumnName="L_LINENUMBER")
    })
    private Lineorder lineorder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PART", insertable=false , updatable=false)
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SUPPLIER", insertable=false , updatable=false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORDERDATE", insertable=false , updatable=false)
    private Date date;

    @Basic
    @Column(name = "O_TOTALPRICE", nullable = true, precision = 0)
    private Double orderTotalPrice;

    @Basic
    @Column(name = "L_DISCOUNT", nullable = true, precision = 0)
    private Double lineItemDiscount;

    @Basic
    @Column(name = "L_EXTENDEDPRICE", nullable = true, precision = 0)
    private Double lineItemExtendedPrice;

    @Basic
    @Column(name = "profit", nullable = true, precision = 0)
    private Double profit;

    @Basic
    @Column(name = "cost", nullable = true, precision = 0)
    private Double cost;

    @Basic
    @Column(name = "turnover", nullable = true, precision = 0)
    private Double turnover;

    @Basic
    @Column(name = "L_QUANTITY", nullable = true)
    private Integer lineItemQuantity;

    @Basic
    @Column(name = "L_TAX", nullable = true, precision = 0)
    private Double lineItemTax;

}
