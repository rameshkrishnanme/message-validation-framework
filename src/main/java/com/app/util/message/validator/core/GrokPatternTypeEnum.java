package com.app.util.message.validator.core;

public enum GrokPatternTypeEnum {
    PLAIN_TEXT, 
    
    PATTERN, 
    
    PARTIAL_PATTERN, 
    
    L_REPEAT_START("[L_REPEAT::]") , 
    
    L_REPEAT_END("[::L_REPEAT]"),
    
    REPEAT_START("[REPEAT::]") , 
    
    REPEAT_END("[::REPEAT]");

    private String reservedPattern;
    

    GrokPatternTypeEnum(String reservedPattern) {
        this.reservedPattern = reservedPattern;
    }

    GrokPatternTypeEnum() {

    }

    
    public String getReservedPattern() {
        return reservedPattern;
    }

    public void setReservedPattern(String reservedPattern) {
        this.reservedPattern = reservedPattern;
    }

}
