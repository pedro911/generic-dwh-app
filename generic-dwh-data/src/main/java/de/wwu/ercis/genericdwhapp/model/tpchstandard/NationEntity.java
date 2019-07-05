package de.wwu.ercis.genericdwhapp.model.tpchstandard;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "nation", schema = "tpch_std_small")
public class NationEntity {

    @Id
    @Column(name = "n_nationkey", nullable = false, precision = 0)
    private Long nNationkey;

    @Basic
    @Column(name = "n_name", nullable = true, length = 25)
    private String nName;

/*    @Basic
    @Column(name = "n_regionkey", nullable = true, precision = 0)
    private Long nRegionkey;*/

    @Basic
    @Column(name = "n_comment", nullable = true, length = 152)
    private String nComment;

    @OneToMany(mappedBy = "nationByCNationkey")
    private Set<CustomerEntity> customersByNNationkey;

    @ManyToOne
    @JoinColumn(name = "n_regionkey", referencedColumnName = "r_regionkey")
    private RegionEntity regionByNRegionkey;

    @OneToMany(mappedBy = "nationBySNationkey")
    private Set<SupplierEntity> suppliersByNNationkey;

}
