package currencyConverter.local.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import currencyConverter.local.valueobjects.CurrencyObject;

public class Calculation {

//	convert from one to another currency
	public BigDecimal calc(CurrencyObject from,CurrencyObject to,BigDecimal quantityfrom) {
				
		BigDecimal result = null;
		MathContext mc = new MathContext(4);
		
//		from from to euro to to
		if(from.getSymbol().equals("€")) {
			result = quantityfrom.multiply(to.getRate(),mc);
		} else if(to.getSymbol().equals("€")) {
			result = quantityfrom.divide(from.getRate(),2,RoundingMode.HALF_EVEN);
		} else {
			result = quantityfrom.divide(from.getRate(),2,RoundingMode.HALF_EVEN);
			result = result.multiply(to.getRate(),mc);
		}
		
		return result;
		
	}
	
}
