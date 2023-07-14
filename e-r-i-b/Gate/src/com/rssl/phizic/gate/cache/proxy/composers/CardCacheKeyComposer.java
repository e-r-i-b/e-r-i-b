package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CardCacheKeyComposer extends AbstractCacheKeyComposer
{
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}

		return buildKey(((Card)args[paramNum]).getId());
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof Card))
			return null;

		Card card = (Card) result;
		return buildKey(card.getId());
	}

	/**
	 * Строит ключ по идентификатору карты.
	 *
	 * @param id внешний идентификатор карты.
	 * @return ключ.
	 */
	public static String buildKey(String id)
	{
		EntityCompositeId cardCompositeId = new EntityCompositeId(id);
		if (cardCompositeId.getLoginId() == null)
			return cardCompositeId.getEntityId();

		return cardCompositeId.getEntityId() + EntityCompositeId.ID_DELIMETER + cardCompositeId.getLoginId();
	}

	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
