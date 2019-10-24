package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class OfferServiceGenericException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Generic error code
	 */
	private ErrorCodeEnum errorCode = ErrorCodeEnum.GENERIC_ERROR;
	
	public OfferServiceGenericException() {
		super();
	}

	public OfferServiceGenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public OfferServiceGenericException(String message) {
		super(message);
	}

	public OfferServiceGenericException errorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	    
	public OfferServiceGenericException errorCode(ErrorCodeEnum errorCode, String message) {
		this.errorCode = errorCode;
		return this;
	}

	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

}
