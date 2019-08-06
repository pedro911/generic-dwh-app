package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class RatioRoot {

    private String id;
    private String name;
    private String parentId;
    private ArrayList<RatioRoot> childrenItems;

    public RatioRoot() {
        this.id = "";
        this.parentId = "";
        this.name = "";
        this.childrenItems = new ArrayList<RatioRoot>();
    }

    public void addChildrenItem(RatioRoot childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }

}
