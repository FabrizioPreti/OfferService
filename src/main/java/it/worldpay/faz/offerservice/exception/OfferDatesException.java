package it.worldpay.faz.offerservice.exception;

import it.worldpay.faz.offerservice.exception.error.ErrorCodeEnum;

public class OfferDatesException extends OfferServiceGenericException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OfferDatesException() {
	}

	/**
	 * @param message
	 */
	public OfferDatesException(String message) {
		super(message);
		errorCode(ErrorCodeEnum.DATE_ERROR);
	}

}
