package com.sev4ikwasd.passwordmanager.web.util;

import java.util.HashMap;
import java.util.Map;

public class ToMapUtil {
    public static Map<String, String> stringsToMap(String string1, String string2) {
        Map<String, String> map = new HashMap<>();
        map.put(string1, string2);
        return map;
    }
}
