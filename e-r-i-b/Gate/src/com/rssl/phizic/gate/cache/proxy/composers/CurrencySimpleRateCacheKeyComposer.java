package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CurrencySimpleRateCacheKeyComposer extends AbstractCacheKeyComposer
{
	public static final String OFFICE_KEY = "office";

	private static final OfficeCacheKeyComposer officeComposer = new OfficeCacheKeyComposer();
	//Currency from, Currency to, CurrencyRateType type, Office office, tarifPlanCode
	public Serializable getKey(Object[] args, String params)
	{
		if(args[0]==null||args[1]==null||args[2]==null||args[3]==null||args[4]==null)
			return null;

		Currency from = (Currency)args[0];
		Currency to = (Currency)args[1];
		CurrencyRateType type = (CurrencyRateType)args[2];
		String tarifPlanCodeType = (String)args[4];

		StringBuilder builder = new StringBuilder();
		builder.append(from.getExternalId());
		builder.append(to.getExternalId());
		builder.append(type);
		String officeStr = officeComposer.getKey(args, "3");
		if(StringHelper.isEmpty(officeStr))
			return null;
		builder.append(officeStr);
		builder.append(tarifPlanCodeType);
		return builder.toString();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(result==null || !(result instanceof CurrencyRate))
			return null;

		//т.к. связанный кеш для данного метода не возможен. исключаем при поиске связанного.
		if(params==null)
             return null;
		CurrencyRate rate = (CurrencyRate)result;

		StringBuilder builder = new StringBuilder();
		builder.append(rate.getFromCurrency().getExternalId());
		builder.append(rate.getToCurrency().getExternalId());
		builder.append(rate.getType());
		String officeStr = officeComposer.getClearCallbackKey(params[3], params);
		if(StringHelper.isEmpty(officeStr))
			return null;
		builder.append(officeStr);
		builder.append(rate.getTarifPlanCodeType());
		return builder.toString();
	}
}
