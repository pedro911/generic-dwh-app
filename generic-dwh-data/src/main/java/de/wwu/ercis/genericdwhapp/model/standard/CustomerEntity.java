package de.wwu.ercis.genericdwhapp.model.standard;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "customer", schema = "tpch_std_small")
public class CustomerEntity {

    @Id
    @Column(name = "c_custkey", nullable = false, precision = 0)
    private long cCustkey;

    @Basic
    @Column(name = "c_mktsegment", nullable = true, length = 10)
    private String cMktsegment;

    @Basic
    @Column(name = "c_name", nullable = true, length = 25)
    private String cName;

    @Basic
    @Column(name = "c_address", nullable = true, length = 40)
    private String cAddress;

    @Basic
    @Column(name = "c_phone", nullable = true, length = 15)
    private String cPhone;

    @Basic
    @Column(name = "c_acctbal", nullable = true, precision = 0)
    private Double cAcctbal;

    @Basic
    @Column(name = "c_comment", nullable = true, length = 118)
    private String cComment;

    @ManyToOne
    @JoinColumn(name = "c_nationkey", referencedColumnName = "n_nationkey")
    private NationEntity nationByCNationkey;

    @OneToMany(mappedBy = "customerByOCustkey")
    private Set<OrdersEntity> ordersByCCustkey;

}
