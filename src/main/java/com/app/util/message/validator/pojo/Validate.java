package com.app.util.message.validator.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Validate {

    private String testPattern;

    private String testMessage;

    private String testResults;

    @JsonInclude(Include.NON_NULL)
    private String responseFormat;
    
    @JsonInclude(Include.NON_NULL)
    private String uniqueIdentifier;
    
    @JsonInclude(Include.NON_NULL)
    private int responseCode;
    
    @JsonInclude(Include.NON_NULL)
    private String responseMsg;

    private boolean base64;

    public boolean isBase64() {
        return base64;
    }

    public void setBase64(boolean base64) {
        this.base64 = base64;
    }

    public String getTestPattern() {
        return testPattern;
    }

    public void setTestPattern(String testPattern) {
        this.testPattern = testPattern;
    }

    public String getTestMessage() {
        return testMessage;
    }

    public void setTestMessage(String testMessage) {
        this.testMessage = testMessage;
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
