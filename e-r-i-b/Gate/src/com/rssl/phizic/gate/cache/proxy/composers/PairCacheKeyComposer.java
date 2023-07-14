package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.common.types.transmiters.Pair;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * считает что ключ, это первый элемент пары
 * работает только для счетов, карт, депозитов
 *
 */
public class PairCacheKeyComposer extends ObjectToEntityCachKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		Pair pair = (Pair)args[getOneParam(params)];
		return super.getKey(new Object[]{pair.getFirst()},"");
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
