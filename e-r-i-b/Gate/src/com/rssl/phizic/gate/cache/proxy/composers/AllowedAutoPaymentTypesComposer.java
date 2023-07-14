package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * Композер для формирования ключа кэша для метода getAllowedAutoPaymentTypes
 * @author niculichev
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class AllowedAutoPaymentTypesComposer extends AbstractCacheKeyComposer
{
	/**
	 * Сформировать ключ
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return ключ
	 */
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		return builder.append(args[0]).append(args[1]).append(args[2]).toString();
	}
}
