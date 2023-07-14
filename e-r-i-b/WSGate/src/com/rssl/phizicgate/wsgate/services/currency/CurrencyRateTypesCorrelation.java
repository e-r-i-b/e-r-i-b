package com.rssl.phizicgate.wsgate.services.currency;


import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 08.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes= new HashMap<Class,Class>();
	public static final Map<Class, Class> toGateTypes= new HashMap<Class,Class>();

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.currency.generated.Office.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.currency.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.currency.generated.Money.class);
		toGeneratedTypes.put(com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class, com.rssl.phizicgate.wsgate.services.currency.generated.Currency.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.CurrencyRateType.class, null);  //прописан в BeanFormatterMap
		toGeneratedTypes.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap


		toGateTypes.put(com.rssl.phizicgate.wsgate.services.currency.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.currency.generated.Currency.class, com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class);

	}
}
