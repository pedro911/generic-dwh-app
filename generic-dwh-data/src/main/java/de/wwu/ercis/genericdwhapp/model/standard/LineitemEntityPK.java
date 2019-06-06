package de.wwu.ercis.genericdwhapp.model.standard;

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
public class LineitemEntityPK implements Serializable {

    @Column(name = "l_orderkey", nullable = false, precision = 0)
    private Long lOrderkey;

    @Column(name = "l_linenumber", nullable = false, precision = 0)
    private Long lLinenumber;

}
