package com.app.util.message.validator.pojo;

public enum ResponseCode {

    SUCCESS(0, "MATCH FOUND SUCCESSFULLY"), 
    
    NO_MATCH(1, "NO MATCH FOUND"), 
    
    PARTIAL_MATCH(2, "PARTIAL MATCH FOUND"), 
    
    RUNTIME_ERROR(3, "RUNTIME ERROR OCCURRED");

    private int code;
    
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
