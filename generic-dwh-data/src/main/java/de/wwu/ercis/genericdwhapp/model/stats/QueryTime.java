package de.wwu.ercis.genericdwhapp.model.stats;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "query_time")
public class QueryTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryTimeId;

    private Timestamp queryExecDate;

    private Double queryTimeMs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "db_model_id")
    private DBModel dbModel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "query_string_id")
    private QueryString queryString;

    public QueryTime(DBModel dbModel, QueryString queryString, Double queryTimeMs, Timestamp queryExecDate) {
        this.dbModel = dbModel;
        this.queryString = queryString;
        this.queryTimeMs = queryTimeMs;
        this.queryExecDate = queryExecDate;
    }
}
