package com.rssl.phizicgate.esberibgate.types.depo;

import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukina
 * @ created 25.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class RecMethodWrapper
{
	private static final Map<String, String> storageMethodMap = new HashMap<String, String>();

	static
	{
		storageMethodMap.put("1","лично");
		storageMethodMap.put("8","через уполномоченных представителей");
		storageMethodMap.put("9","заказным письмом с уведомлением");
		storageMethodMap.put("14","через доверенное лицо");
		storageMethodMap.put("10","в соответствии с соглашением є ");
	}

	/**
	 * ѕолучить способ приема поручений от владельца счета депо/способ передачи информации владельцу счета депо
	 * @param key - ключ
	 * @return String
	 */
	public static String getRecMethod(String key)
	{
		if(StringHelper.isEmpty(key))
			return null;
		return storageMethodMap.get(key);
	}
}
