package de.wwu.ercis.genericdwhapp.model.starschema;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FactResult {

    private String dimension;

    private String name;

    private String ratio;

    private Double value;

}
