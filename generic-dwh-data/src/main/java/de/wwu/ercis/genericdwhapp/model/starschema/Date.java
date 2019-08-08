package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "dim_date")
public class Date {

    @Id
    @Column(name = "DATE_PK", nullable = true)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "date")
    private List<StarSchemaFact> starSchemaFacts = new ArrayList<>();

    @Basic
    @Column(name = "YEAR_NUMBER", nullable = true)
    private Integer yearNumber;

    @Basic
    @Column(name = "MONTH_NUMBER", nullable = true)
    private Integer monthNumber;

    @Basic
    @Column(name = "DAY_OF_YEAR_NUMBER", nullable = true)
    private Integer dayOfYearNumber;

    @Basic
    @Column(name = "DAY_OF_MONTH_NUMBER", nullable = true)
    private Integer dayOfMonthNumber;

    @Basic
    @Column(name = "DAY_OF_WEEK_NUMBER", nullable = true)
    private Integer dayOfWeekNumber;

    @Basic
    @Column(name = "WEEK_OF_YEAR_NUMBER", nullable = true)
    private Integer weekOfYearNumber;

    @Basic
    @Column(name = "DAY_NAME", nullable = true, length = 30)
    private String dayName;

    @Basic
    @Column(name = "MONTH_NAME", nullable = true, length = 30)
    private String monthName;

    @Basic
    @Column(name = "QUARTER_NUMBER", nullable = true)
    private Integer quarterNumber;

    @Basic
    @Column(name = "QUARTER_NAME", nullable = true, length = 2)
    private String quarterName;

    @Basic
    @Column(name = "YEAR_QUARTER_NAME", nullable = true, length = 32)
    private String yearQuarterName;

    @Basic
    @Column(name = "WEEKEND_IND", nullable = true, length = 1)
    private String weekendInd;

    @Basic
    @Column(name = "DAYS_IN_MONTH_QTY", nullable = true)
    private Integer daysInMonthQty;

    @Basic
    @Column(name = "DAY_DESC", nullable = true, length = 10)
    private String dayDesc;

    @Basic
    @Column(name = "WEEK_SK", nullable = true)
    private Integer weekSk;

    @Basic
    @Column(name = "DAY_DATE", nullable = true)
    private Timestamp dayDate;

    @Basic
    @Column(name = "WEEK_NAME", nullable = true, length = 32)
    private String weekName;

    @Basic
    @Column(name = "WEEK_OF_MONTH_NUMBER", nullable = true)
    private Integer weekOfMonthNumber;

    @Basic
    @Column(name = "WEEK_OF_MONTH_NAME", nullable = true, length = 2)
    private String weekOfMonthName;

    @Basic
    @Column(name = "YEAR_SK", nullable = true)
    private Integer yearSk;

    @Basic
    @Column(name = "MONTH_SK", nullable = true)
    private Integer monthSk;

    @Basic
    @Column(name = "QUARTER_SK", nullable = true)
    private Integer quarterSk;

    @Basic
    @Column(name = "DAY_OF_WEEK_SORT_NAME", nullable = true, length = 60)
    private String dayOfWeekSortName;

    @Basic
    @Column(name = "YEAR_SORT_NUMBER", nullable = true, length = 4)
    private String yearSortNumber;

}
