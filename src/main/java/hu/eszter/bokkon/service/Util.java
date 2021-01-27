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
        if (map.isEmpty()) {
            System.out.println("Change is not available!");
        }
        for (String key : map.keySet()) {
            String[] values = map.get(key).split(",");
            System.out.println( (needsPlural(key) ? key + "s" : key)
                    + " for:"
                    + String.join("", Collections.nCopies((needsPlural(key) ? 15 : 16) - key.length(), " "))
                    + (needsPlural(values[0]) ? values[0] + "s" :values[0])
                    + (values.length > 1 ? " or " + (needsPlural(values[1]) ? values[1] + "s" :values[1]) : " "));
        }
    }

    private boolean needsPlural(String word) {
        return word.contains(" ") && !word.contains("Sheep");
    }

}
