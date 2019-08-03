package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class DimensionRoot {

    private String id;
    private String name;
    private String parentId;
    private ArrayList<DimensionRoot> childrenItems;

    public DimensionRoot() {
        this.id = "";
        this.parentId = "";
        this.name = "";
        this.childrenItems = new ArrayList<DimensionRoot>();
    }

    public void addChildrenItem(DimensionRoot childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }

}
