package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class FactPK implements Serializable {

    @Id
    @Column(name = "ratio_id", nullable = false)
    private Long ratioId;

    @Id
    @Column(name = "reference_object_id", nullable = false)
    private Long referenceObjectId;

}
