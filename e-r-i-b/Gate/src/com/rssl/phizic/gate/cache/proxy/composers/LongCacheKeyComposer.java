package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * @author krenev
 * @ created 21.07.2010
 * @ $Author$
 * @ $Revision$
 *
 * »спользовать дл€ методов c ключевым полем типа Long.
 * ѕор€дковый номер в массиве аргументов метода ключевого параметра можно передать в параметрах аннотации.
 */
public class LongCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return (Long)args[paramNum];
	}
}
