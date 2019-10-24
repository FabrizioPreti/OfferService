package it.worldpay.faz.offerservice.exception.error;

/**
 * Error codes
 *
 */
public enum ErrorCodeEnum {
	
	GENERIC_ERROR("GENERIC", "Generic Internal Error"),
    NOT_FOUND_ERROR("OFF-SERVICE-01", "Resource Not Found"),
    DUPLICATED_ERROR("OFF-SERVICE-02", "Resource is alredy present"),
    EXPIRED_ERROR("OFF-SERVICE-03", "Offer is expired"),
    DATE_ERROR("OFF-SERVICE-04","Offer dates are not correct"),
    IS_NOT_AVAILABLE("OFFE-SERVICE-05", "The Server is not available at the moment")
    ;


    /**
     * the code
     */
    String code;
    /**
     * the default error message
     */
    String defaultMessage;

    /**
     * @param code
     * @param defaultMessage
     */
    ErrorCodeEnum(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the default error message
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

}
