package de.wwu.ercis.genericdwhapp.repositories;

import java.util.List;

public interface NativeQuery {

    List<String[]> nativeQuery(String query);

}
