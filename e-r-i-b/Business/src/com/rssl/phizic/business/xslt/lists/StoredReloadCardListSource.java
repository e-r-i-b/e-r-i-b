package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * Класс для получения xml-списка карт, который предварительно запрашивает карты из шины. Позволяет использовать оффлайн-продукты
 * в качестве источника данных
 * @author Pankin
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class StoredReloadCardListSource extends ReloadCardListSource
{
	public StoredReloadCardListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredReloadCardListSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public StoredReloadCardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected boolean skipStoredResource(Card card)
	{
		return true;
	}
}
