package com.telstra.newsfeed.exception;

/**
 * 
 * ClassName: ErrorCodeAndMessage.java
 * 
 * Description: This class to list out all possible error code and their
 * respective user message.
 * 
 */
public enum ErrorCodeAndMessage implements java.io.Serializable {

	
	

	UNCAUGHT_ERROR("1", "An internal error occurred.If the problem continues, please contact your HSP Support Team");

	
	private final String errorCode;

	/**
	 * Message Associated with the error code.
	 */
	private final String errorMessage;

	/**
	 * Overloaded constructor
	 * @param code: error code 
	 * @param msg: user message
	 */
	private ErrorCodeAndMessage(String code,String msg) {
		errorCode = code;
		errorMessage = msg;
	}

	/**
	 * getter method for error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * getter method for user message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
