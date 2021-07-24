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
public enum ResultType {

    OK(100),
    NO(200),
    ERROR(900);

    @Getter
    private final Integer value;

    private static final Map<Integer, ResultType> values;
    static {
        values = new HashMap<>();
        for (ResultType e : ResultType.values()) {
            values.put(e.value, e);
        }
    }

    public static ResultType fromValue(Object value) {
        assert value != null;
        return fromValue(value.toString());
    }

    public static ResultType fromValue(String value) {
        assert value != null;
        return values.get(Integer.parseInt(value.trim()));
    }
}
