package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.io.Serializable;

/**
 * @author niculichev
 * @ created 20.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferExternalIdCacheKeyComposer extends StringCacheKeyComposer
{
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

	/**
	 * Поддерживается ли формирование ключа из результата
	 * @return true - поддерживается.
	 */
	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
