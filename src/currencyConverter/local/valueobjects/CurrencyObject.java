/**
 * 
 */
package currencyConverter.local.valueobjects;

/**
 * @author mortlust
 *
 */
public class CurrencyObject extends ReferenceRates {
	
	private String displayName;
	private int numericCode;
	
	/**
	 * Constructor
	 */
	public CurrencyObject(String symbol,String rate,String displayName,int numericCode) {
		super(symbol,rate);
		this.displayName = displayName;
		this.numericCode = numericCode;
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the numericCode
	 */
	public int getNumericCode() {
		return numericCode;
	}

//	toString method
	public String toString() {
		return "Name:		" + displayName + "\nSymbol:		" + getSymbol() + "\nNumeric Code:	" + numericCode + "\nRate:		" + getRate();
	}
}
