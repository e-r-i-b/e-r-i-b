package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 26.11.2012
 * Time: 16:51:26
 */
public class StoredAccountCardCurrencyListSource extends AccountCardCurrencyListSource
{
	public StoredAccountCardCurrencyListSource(EntityListDefinition definition, String nationCurrency) throws GateException, GateLogicException
	{
		super(definition, nationCurrency);
	}

	public StoredAccountCardCurrencyListSource(EntityListDefinition definition, Map parameters) throws GateException, GateLogicException
	{
		super(definition, parameters);
	}

	@Override
	protected boolean skipStoredResource(Object object)
	{
		return true;
	}
}
