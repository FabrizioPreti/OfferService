package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class OfferExpiredException extends OfferServiceGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OfferExpiredException() {
	}

	/**
	 * @param message
	 */
	public OfferExpiredException(String message) {
		super(message);
		errorCode(ErrorCodeEnum.EXPIRED_ERROR);
	}

}
