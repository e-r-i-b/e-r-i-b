package com.rssl.phizic.gate.cache.proxy.composers;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Выписка по определенному числу операций.
 */
public class NumberAbstractCacheKeyComposer extends ObjectToEntityCachKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		StringBuilder key = new StringBuilder();
		//получаем ключ по сущности
		key.append(super.getKey(args, params));
		//учитываем в ключе кол-во операций
		key.append( FullAbstractCacheKeyComposer.SEPARATOR );
		key.append( Long.toString((Long)args[0]));

		return key.toString();
	}
}
