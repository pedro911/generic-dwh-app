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
@Table(name = "supplier", schema = "tpch_std_small")
public class SupplierEntity {

    @Id
    @Column(name = "s_suppkey", nullable = false, precision = 0)
    private Long sSuppkey;

    @Basic
    @Column(name = "s_comment", nullable = true, length = 102)
    private String sComment;

    @Basic
    @Column(name = "s_name", nullable = true, length = 25)
    private String sName;

    @Basic
    @Column(name = "s_address", nullable = true, length = 40)
    private String sAddress;

    @Basic
    @Column(name = "s_phone", nullable = true, length = 15)
    private String sPhone;

    @Basic
    @Column(name = "s_acctbal", nullable = true, precision = 0)
    private Double sAcctbal;

    @OneToMany(mappedBy = "supplierByPsSuppkey")
    private Set<PartsuppEntity> partsuppsBySSuppkey;

    @ManyToOne
    @JoinColumn(name = "s_nationkey", referencedColumnName = "n_nationkey")
    private NationEntity nationBySNationkey;

}
