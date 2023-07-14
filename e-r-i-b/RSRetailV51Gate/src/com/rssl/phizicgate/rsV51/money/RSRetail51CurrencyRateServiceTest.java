package com.rssl.phizicgate.rsV51.money;

import com.rssl.phizicgate.rsV51.test.RSRetaileGateTestCaselBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 18.04.2007
 * @ $Author$
 * @ $Revision$
 */
public class RSRetail51CurrencyRateServiceTest extends RSRetaileGateTestCaselBase
{

	public void testgetRate() throws GateException, GateLogicException
	{
        CurrencyRateService service = gateFactory.service(CurrencyRateService.class);
//		CurrencyRateService service = new RSRetail51CurrencyRateService(gateFactory);

		CurrencyService currencyService = gateFactory.service(CurrencyService.class);
		Currency currencyRUR = currencyService.findByAlphabeticCode("RUB");
		Currency currencyUSD = currencyService.findByAlphabeticCode("USD");
		assertNotNull(currencyRUR);
		assertNotNull(currencyUSD);

		//todo
//		CurrencyRate currencyRate = service.getRate(currencyUSD, new Money(new BigDecimal(100), currencyRUR));
//		assertNotNull(currencyRate);
//
//		currencyRate = service.getRate(new Money(new BigDecimal(100), currencyRUR),currencyUSD);
//		assertNotNull(currencyRate);
//
//		currencyRate = service.getRate(currencyRUR, new Money(new BigDecimal(100), currencyUSD));
//		assertNotNull(currencyRate);
//
//		currencyRate = service.getRate(new Money(new BigDecimal(100), currencyUSD), currencyRUR);
//		assertNotNull(currencyRate);
//
//		currencyRate = service.getRate(currencyUSD, currencyRUR, CurrencyRateType.SALE);
//		assertNotNull(currencyRate);
//
//		currencyRate = service.getRate(currencyUSD, currencyRUR, CurrencyRateType.BUY);
//		assertNotNull(currencyRate);

	}
}
