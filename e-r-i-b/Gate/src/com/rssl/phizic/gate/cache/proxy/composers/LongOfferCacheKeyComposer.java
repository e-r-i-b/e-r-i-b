package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.io.Serializable;

/**
 * @author niculichev
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferCacheKeyComposer extends AbstractCacheKeyComposer
{

	/**
	 * Сформировать ключ
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return ключ
	 */
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return ((LongOffer) args[paramNum]).getExternalId();
	}

	/**
	 * Формирует ключ аналогично getKey, но из результата выполнения запроса.
	 * @param result результат выполнения метода
	 * @param params дополнительные параметры
	 * @return ключ, или null если преобразование не может быть выполнено.
	 */
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof LongOffer))
			return null;

		LongOffer longOffer = (LongOffer) result;
		return longOffer.getExternalId();
	}
}
