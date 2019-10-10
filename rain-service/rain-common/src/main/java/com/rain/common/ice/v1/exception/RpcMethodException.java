package com.rain.common.ice.v1.exception;

public class RpcMethodException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7506045092817943877L;
    
	public RpcMethodException(){
		super();
	}
	
	public RpcMethodException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public RpcMethodException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RpcMethodException(Throwable cause) {
		super(cause);
	}
	
	public RpcMethodException(String message) {
		super(message);
	}	
}
