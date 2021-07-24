/*
 * Author: rok_root
 * Created time: 2021. 07. 24
 * Copyrights rok_root. All rights reserved.
 */

package kr.co.rokroot.core.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@AllArgsConstructor
public enum DatabaseType {

    MariaDB("Maria database"),
    OracleDB("Oracle database"),
    MicrosoftDB("MS-SQL database");

    @Getter
    private final String desc;

    private static final Map<String, DatabaseType> values;
    static {
        values = new HashMap<>();
        for (DatabaseType e : DatabaseType.values()) {
            values.put(e.desc, e);
        }
    }

    public static DatabaseType fromValue(Object value) {
        assert value != null;
        return fromValue(value.toString());
    }

    public static DatabaseType fromValue(String value) {
        assert value != null;
        return values.get(value.trim());
    }
}
