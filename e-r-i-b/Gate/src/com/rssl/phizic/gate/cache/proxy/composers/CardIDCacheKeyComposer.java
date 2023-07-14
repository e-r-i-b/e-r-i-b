package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Card;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CardIDCacheKeyComposer extends StringCacheKeyComposer
{
	@Override
	public Serializable getKey(Object[] args, String params)
	{
		return CardCacheKeyComposer.buildKey((String) super.getKey(args, params));
	}

	@Override 
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Card))
			return null;
		
		Card card = (Card)result;
		return CardCacheKeyComposer.buildKey(card.getId());
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}