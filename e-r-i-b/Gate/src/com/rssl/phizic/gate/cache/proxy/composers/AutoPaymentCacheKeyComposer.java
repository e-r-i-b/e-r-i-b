package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 *  композер для методов, которые в качестве параметров получают автоплатеж
 */
public class AutoPaymentCacheKeyComposer extends AbstractCacheKeyComposer
{
	/**
	 * Сформировать ключ
	 * @param args аргументы вызова метода
	 * @param params параметры для работа композера
	 * @return ключ
	 */
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if (!StringHelper.isEmpty(params))
			paramNum = Integer.parseInt(params);

		return ((AutoPayment) args[paramNum]).getExternalId();
	}

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
