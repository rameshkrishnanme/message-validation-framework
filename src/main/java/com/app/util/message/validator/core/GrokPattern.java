package com.app.util.message.validator.core;

public class GrokPattern {

	private String regEx;
	
	private String description;
	
	private String name;
	
	private String fieldName;
	
	private GrokPatternTypeEnum grokPatternType;
	
    public GrokPattern(String regEx, GrokPatternTypeEnum grokPatternType, String description, String name, 
        String fieldName) {
        super();
        this.regEx = regEx;
        this.description = description;
        this.name = name;
        this.fieldName = fieldName;
        this.grokPatternType = grokPatternType;
    }
    
    public GrokPattern(GrokPatternTypeEnum grokPatternType) {
        this.grokPatternType = grokPatternType;
    }
    
    public GrokPattern(String regEx, GrokPatternTypeEnum grokPatternType) {
        this.regEx = regEx;
        this.grokPatternType = grokPatternType;
    }
    
    public GrokPattern() {
        
    }

    public String getRegEx() {
        return regEx;
    }
    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public GrokPatternTypeEnum getGrokPatternType() {
        return grokPatternType;
    }
    public void setGrokPatternType(GrokPatternTypeEnum grokPatternType) {
        this.grokPatternType = grokPatternType;
    }
	
	

}
