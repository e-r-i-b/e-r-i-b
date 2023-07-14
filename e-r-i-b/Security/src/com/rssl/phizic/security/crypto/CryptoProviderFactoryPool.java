package com.rssl.phizic.security.crypto;

import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 15.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Пул фабрик крипто-провайдеров.
 * Содержит мап "название_провайдера -> фабрика"
 */
class CryptoProviderFactoryPool implements Serializable
{
	private final Map<String, CryptoProviderFactory> factories
			= new HashMap<String, CryptoProviderFactory>();

	private CryptoProviderFactory defaultFactory = null;

	///////////////////////////////////////////////////////////////////////////

	CryptoProviderFactory getFactory(String name)
	{
		if (StringHelper.isEmpty(name))
			throw new IllegalArgumentException("Аргумент 'name' не может быть пустым");
		return factories.get(name);
	}

	void putFactory(CryptoProviderFactory factory)
	{
		if (factory == null)
			throw new NullPointerException("Аргумент 'factory' не может быть null");
		factories.put(factory.getName(), factory);
	}

	CryptoProviderFactory getDefaultFactory()
	{
		return defaultFactory;
	}

	void setDefaultFactory(CryptoProviderFactory defaultFactory)
	{
		this.defaultFactory = defaultFactory;
	}

	void close()
	{
		for (CryptoProviderFactory factory : factories.values())
			factory.close();

		if (defaultFactory != null)
			defaultFactory.close();
	}
}