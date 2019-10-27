package it.worldpay.faz.offerservice.dto;

import java.util.Date;

/**
 * @author FPreti
 *
 */
public class BaseCurrencyDTO {
	
	private RatesDTO rates;
	private String base;
	private Date date;
	
	
	public RatesDTO getRates() {
		return rates;
	}
	public void setRates(RatesDTO rates) {
		this.rates = rates;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "BaseCurrencyDTO [rates=" + rates + ", base=" + base + ", date=" + date + "]";
	}
	
	
}
