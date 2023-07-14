package com.rssl.phizicgate.esberibgate.types.depo;

import com.rssl.phizic.gate.depo.DepoAccountSecurityStorageMethod;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import java.util.HashMap;

/**
 * Соответствие значений метода хранения ценных бумаг на шине и в СБОЛ.
 * @author mihaylov
 * @ created 24.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountStorageMethodWrapper
{
	private static final Map<String, DepoAccountSecurityStorageMethod> storageMethodMap = new HashMap<String, DepoAccountSecurityStorageMethod>();

	static
	{
		storageMethodMap.put("1",DepoAccountSecurityStorageMethod.open);
		storageMethodMap.put("2",DepoAccountSecurityStorageMethod.closed);
		storageMethodMap.put("4",DepoAccountSecurityStorageMethod.markByNominal);
		storageMethodMap.put("5",DepoAccountSecurityStorageMethod.markByNominalAndCoupon);
		storageMethodMap.put("6",DepoAccountSecurityStorageMethod.markByCoupon);
	}

	/**
	 * Получить метод хранения ценных бумаг по ключу из шины
	 * @param key - ключ
	 * @return DepoAccountSecurityStorageMethod
	 */
	public static DepoAccountSecurityStorageMethod getStorageMethod(String key)
	{
		if(StringHelper.isEmpty(key))
			return null;
		return storageMethodMap.get(key);
	}
}
