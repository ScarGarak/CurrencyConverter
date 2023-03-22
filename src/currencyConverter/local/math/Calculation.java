package currencyConverter.local.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import currencyConverter.local.valueobjects.CurrencyObject;

public class Calculation {

//	convert from one to another currency
	public BigDecimal calc(CurrencyObject from,CurrencyObject to,BigDecimal quantityfrom) {
				
//		ensures that the output will be like 10 instead of 1e+1
		BigDecimal result = null;
//		BigDecimal conv = BigDecimal.valueOf(quantityfrom);
		MathContext mc = new MathContext(4);
		
//		from from to euro to to
		if(from.getSymbol().equals("€")) {
//			conv = BigDecimal.valueOf(quantityfrom);
			result = quantityfrom.multiply(to.getRate(),mc);
		} else if(to.getSymbol().equals("€")) {
//			conv = BigDecimal.valueOf(quantityfrom);
			result = quantityfrom.divide(from.getRate(),2,RoundingMode.HALF_EVEN);
		} else {
//			conv = BigDecimal.valueOf(quantityfrom);
			result = quantityfrom.divide(from.getRate(),2,RoundingMode.HALF_EVEN);
			result = result.multiply(to.getRate(),mc);
		}
		
		return result;
		
	}
	
}
