package com.app.util.message.validator.pattern.pojo;

import com.app.util.message.validator.pojo.ResponseCode;

public class MessageValidResponse {

	private ResponseCode status;
	
	private String jsonPlainText;
	 
	// private List<String> errorDetails;
	
    public ResponseCode getStatus() {
        return status;
    }
    public void setStatus(ResponseCode status) {
        this.status = status;
    }
   
    public String getJsonPlainText() {
        return jsonPlainText;
    }
    public void setJsonPlainText(String jsonPlainText) {
        this.jsonPlainText = jsonPlainText;
    }
	
	
	
	


}
