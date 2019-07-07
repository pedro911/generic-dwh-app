package de.wwu.ercis.genericdwhapp.model.genericdwh;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DimensionRoot {

    private String id;
    private String parentId;
    private List<DimensionRoot> childrenItems;

    public DimensionRoot() {
        this.id = "";
        this.parentId = "";
        this.childrenItems = new ArrayList<DimensionRoot>();
    }

    public void addChildrenItem(DimensionRoot childrenItem){
        if(!this.childrenItems.contains(childrenItem))
            this.childrenItems.add(childrenItem);
    }

    @Override
    public String toString() {
        return "DimensionRoot{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", childrenItems=" + childrenItems +
                '}';
    }
}
