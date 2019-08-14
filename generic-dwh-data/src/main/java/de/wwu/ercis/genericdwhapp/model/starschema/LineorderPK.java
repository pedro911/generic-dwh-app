package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class LineorderPK implements Serializable {

    @Id
    @Column(name = "O_ORDERKEY", nullable = false)
    private int orderKey;

    @Id
    @Column(name = "L_LINENUMBER", nullable = false)
    private int lineNumber;

}
