package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.locale.MultiLocaleContext;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 09.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyNumberCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String DELIMITER = "|";

	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return args[paramNum] + DELIMITER + MultiLocaleContext.getLocaleId();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Currency))
			return null;

		Currency currency = (Currency)result;
		return currency.getNumber() + DELIMITER + MultiLocaleContext.getLocaleId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
