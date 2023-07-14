package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 *  композер для методов, которым в качестве параметра приходит идентификатор автоплатежа
 */
public class AutoPaymentExternalIdCacheKeyComposer extends StringCacheKeyComposer
{
	/**
	 * Формирует ключ аналогично getKey, но из результата выполнения запроса.
	 * @param result результат выполнения метода
	 * @param params дополнительные параметры
	 * @return ключ, или null если преобразование не может быть выполнено.
	 */
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result == null || !(result instanceof AutoPayment))
			return null;
		AutoPayment autoPayment = (AutoPayment) result;
		return autoPayment.getExternalId();
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
