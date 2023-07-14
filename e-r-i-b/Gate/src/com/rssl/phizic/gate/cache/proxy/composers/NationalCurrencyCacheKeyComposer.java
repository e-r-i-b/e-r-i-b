package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.config.locale.MultiLocaleContext;

import java.io.Serializable;

/**
 * @author lepihina
 * @ created 10.06.15
 * $Author$
 * $Revision$
 */
public class NationalCurrencyCacheKeyComposer extends AbstractCacheKeyComposer
{
	private static final String DELIMITER = "|";
	private static final String KEY = "NATIONAL_CURRENCY";

	public Serializable getKey(Object[] args, String params)
	{
		return KEY + DELIMITER + MultiLocaleContext.getLocaleId();
	}
}