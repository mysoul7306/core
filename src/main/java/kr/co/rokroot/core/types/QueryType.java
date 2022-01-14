/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@AllArgsConstructor
public enum QueryType {

    SELECT_ONE("Select one row"),
    SELECT_LIST("Select list row"),
    INSERT("Insert rows"),
    UPDATE("Update rows"),
    DELETE("Delete rows");

    @Getter
    private final String desc;

    private static final Map<String, QueryType> values;
    static {
        values = new HashMap<>();
        for (QueryType e : QueryType.values()) {
            values.put(e.desc, e);
        }
    }

    public static QueryType fromValue(Object value) {
        assert value != null;
        return fromValue(value.toString());
    }

    public static QueryType fromValue(String value) {
        assert value != null;
        return values.get(value.trim());
    }

}