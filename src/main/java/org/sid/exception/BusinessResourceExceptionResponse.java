package org.sid.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BusinessResourceExceptionResponse {

	private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;
 
    public BusinessResourceExceptionResponse() {
    }
}
