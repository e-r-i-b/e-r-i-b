package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.cache.proxy.CacheKeyComposer;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * »спользовать дл€ методов c ключевым полем типа String.
 * ѕор€дковый номер в массиве аргументов метода ключевого параметра можно передать в параметрах аннотации.
 */
public class StringCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return (String)args[paramNum];
	}
}
