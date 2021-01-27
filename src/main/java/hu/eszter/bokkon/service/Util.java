package hu.eszter.bokkon.service;

import java.util.Collections;
import java.util.Map;

public class Util {

    public void printMapInt(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            System.out.println(key
                    + String.join("", Collections.nCopies(12 - key.length(), " "))
                    + ": "
                    + map.get(key).toString());
        }
    }

    public void printMapStr(Map<String, String> map) {
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println( (key.contains(" ") && !key.contains("Sheep") ? key + "s" : key)
                    + String.join("", Collections.nCopies(12 - key.length(), " "))
                    + ": "
                    + (value.contains(" ") && !value.contains("Sheep") ? value + "s" :value));
        }
    }

}
