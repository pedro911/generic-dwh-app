package de.wwu.ercis.genericdwhapp.model.tpchstandard;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "part", schema = "tpch_std_small")
public class PartEntity {

    @Id
    @Column(name = "p_partkey", nullable = false, precision = 0)
    private Long pPartkey;

    @Basic
    @Column(name = "p_type", nullable = true, length = 25)
    private String pType;

    @Basic
    @Column(name = "p_size", nullable = true, precision = 0)
    private BigInteger pSize;

    @Basic
    @Column(name = "p_brand", nullable = true, length = 10)
    private String pBrand;

    @Basic
    @Column(name = "p_name", nullable = true, length = 55)
    private String pName;

    @Basic
    @Column(name = "p_container", nullable = true, length = 10)
    private String pContainer;

    @Basic
    @Column(name = "p_mfgr", nullable = true, length = 25)
    private String pMfgr;

    @Basic
    @Column(name = "p_retailprice", nullable = true, precision = 0)
    private Double pRetailprice;

    @Basic
    @Column(name = "p_comment", nullable = true, length = 23)
    private String pComment;

    @OneToMany(mappedBy = "partByPsPartkey")
    private Set<PartsuppEntity> partsuppsByPPartkey;

}
