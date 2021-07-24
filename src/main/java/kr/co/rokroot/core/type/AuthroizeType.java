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
public enum AuthroizeType {

    NO("no required"),
    JWT("JSON web token"),
    CSRF("cross-site request forgery");

    @Getter
    private final String desc;

    private static final Map<String, AuthroizeType> values;
    static {
        values = new HashMap<>();
        for (AuthroizeType e : AuthroizeType.values()) {
            values.put(e.desc, e);
        }
    }

    public static AuthroizeType fromValue(Object value) {
        assert value != null;
        return fromValue(value.toString());
    }

    public static AuthroizeType fromValue(String value) {
        assert value != null;
        return values.get(value.trim());
    }
}
