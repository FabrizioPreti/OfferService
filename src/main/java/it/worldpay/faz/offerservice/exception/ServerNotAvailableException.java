package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class ServerNotAvailableException extends OfferServiceGenericException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServerNotAvailableException() {};

	/**
	 * @param message
	 */
	public ServerNotAvailableException(String message) {
		super(message);
		errorCode(ErrorCodeEnum.NOT_FOUND_ERROR);
	}

}
