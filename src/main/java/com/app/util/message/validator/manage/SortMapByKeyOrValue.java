package com.app.util.message.validator.manage;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.math.NumberUtils;

public class SortMapByKeyOrValue {
    
    public static Map<String, Integer> sortByValue(boolean order, Map<String, Integer> map) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

   public static Map<String, Object> sortByKey(boolean order,  Map<String, Object> map) {
        /*List<Entry<String, Object>> list = new LinkedList<Entry<String, Object>>(map.entrySet());
        Collections.sort(list, comp(order));*/
        Map<String, Object> sortedMap = new TreeMap<String, Object>(comp(true));
        final Set<Entry<String, Object>> entrySet = map.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

   private static Comparator<? super String> comp(boolean order) {
    return new Comparator<String>() {
        public int compare(String o1, String o2) {
            
            String n1 = o1.replaceAll("\\D", "");
            String n2 = o2.replaceAll("\\D", "");
            
            Integer i1 = new Integer(n1);
            Integer i2 = new Integer(n2);
            
            if (order) {
                return i1.compareTo(i2);
            }
            else {
                return i1.compareTo(i2);

            }
        }
    };
}

}