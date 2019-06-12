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
public class RatioCombinationPK implements Serializable {

    @Id
    @Column(name = "combination_id", nullable = false)
    private Long combinationId;

    @Id
    @Column(name = "subordinate_id", nullable = false)
    private Long subordinateId;

}
