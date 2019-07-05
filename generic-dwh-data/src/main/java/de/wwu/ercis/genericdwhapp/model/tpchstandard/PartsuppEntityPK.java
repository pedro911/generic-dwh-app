package de.wwu.ercis.genericdwhapp.model.tpchstandard;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Embeddable
public class PartsuppEntityPK implements Serializable {

    @Column(name = "ps_partkey", nullable = false, precision = 0)
    private Long psPartkey;

    @Column(name = "ps_suppkey", nullable = false, precision = 0)
    private Long psSuppkey;

}
