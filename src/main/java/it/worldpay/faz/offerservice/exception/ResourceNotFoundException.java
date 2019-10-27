package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class ResourceNotFoundException extends OfferServiceGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
	}

	/**
	 * @param message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
		errorCode(ErrorCodeEnum.NOT_FOUND_ERROR);
	}

}
