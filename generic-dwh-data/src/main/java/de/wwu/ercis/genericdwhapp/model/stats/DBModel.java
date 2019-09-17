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
@Table(name = "db_model")
public class DBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbModelId;

    private String dbName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbModel")
    private List<QueryTime> queryTimeList = new ArrayList<>();

    public DBModel(String db) {
        this.dbName = db;
    }
}
