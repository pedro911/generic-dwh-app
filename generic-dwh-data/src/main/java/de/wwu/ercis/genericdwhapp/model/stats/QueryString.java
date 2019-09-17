package de.wwu.ercis.genericdwhapp.model.stats;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "query_string")
public class QueryString {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryStringId;

    private String refObjRatios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "queryString")
    private List<QueryTime> queryTimeList = new ArrayList<>();

    public QueryString(String refObjRatio) {
        this.refObjRatios = refObjRatio;
    }
}
