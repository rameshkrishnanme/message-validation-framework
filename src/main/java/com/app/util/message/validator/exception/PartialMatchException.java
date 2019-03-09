package com.app.util.message.validator.exception;

public class PartialMatchException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8744866556267914571L;

    
    public PartialMatchException() {
        super("Partial Match Found");
    }
}
