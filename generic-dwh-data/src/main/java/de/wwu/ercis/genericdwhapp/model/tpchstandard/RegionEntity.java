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
@Table(name = "region", schema = "tpch_std_small")
public class RegionEntity {

    @Id
    @Column(name = "r_regionkey", nullable = false, precision = 0)
    private Long rRegionkey;

    @Basic
    @Column(name = "r_name", nullable = true, length = 25)
    private String rName;

    @Basic
    @Column(name = "r_comment", nullable = true, length = 152)
    private String rComment;

    @OneToMany(mappedBy = "regionByNRegionkey")
    private Set<NationEntity> nationsByRRegionkey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionEntity that = (RegionEntity) o;

        if (rRegionkey != that.rRegionkey) return false;
        if (rName != null ? !rName.equals(that.rName) : that.rName != null) return false;
        if (rComment != null ? !rComment.equals(that.rComment) : that.rComment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (rRegionkey ^ (rRegionkey >>> 32));
        result = 31 * result + (rName != null ? rName.hashCode() : 0);
        result = 31 * result + (rComment != null ? rComment.hashCode() : 0);
        return result;
    }

}
