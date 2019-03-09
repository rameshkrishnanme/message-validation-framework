package com.app.util.message.validator.manage;

import java.util.Comparator;

public class NumericKeyComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        Integer i1 = new Integer(s1);
        Integer i2 = new Integer(s2);
        
        return i2.compareTo(i1);
    }
    

    
   
}
