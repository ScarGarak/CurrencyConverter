package currencyConverter.local.valueobjects;

import java.math.BigDecimal;

public abstract class ReferenceRates {

	private String symbol;
	private BigDecimal rate;
	
	public ReferenceRates(String symbol,String rate) {
		this.symbol = symbol;
		this.rate = new BigDecimal(rate);
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

}
