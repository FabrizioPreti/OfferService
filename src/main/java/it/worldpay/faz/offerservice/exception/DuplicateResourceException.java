package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class DuplicateResourceException extends OfferServiceGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateResourceException() {
	}

	/**
	 * @param message
	 */
	public DuplicateResourceException(String message) {
		super(message);
		errorCode(ErrorCodeEnum.DUPLICATED_ERROR);
	}
	
	
	
	
}
