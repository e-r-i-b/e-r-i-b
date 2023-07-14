package com.rssl.phizic.web.gate.services.currency;

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
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот
//	public static final Map types      = new HashMap<Class,Class>();   // наоборот


	static
	{
//		toGeneratedTypes.put(com.rssl.phizic.business.dictionaries.currencies.Currency.class, com.rssl.phizic.web.gate.services.currency.generated.Currency.class);
		toGeneratedTypes.put(com.rssl.phizic.web.gate.services.types.CurrencyImpl.class, com.rssl.phizic.web.gate.services.currency.generated.Currency.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.CurrencyRateType.class, null);  //прописан в BeanFormatterMap
		toGeneratedTypes.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);  //прописан в BeanFormatterMap

		toGateTypes.put(com.rssl.phizic.web.gate.services.currency.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.currency.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
        toGateTypes.put(com.rssl.phizic.web.gate.services.currency.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.currency.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate.class);


	}

}
