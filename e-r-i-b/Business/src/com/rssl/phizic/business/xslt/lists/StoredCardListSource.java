package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 *
 * Разрешает использование оффлайн вкладов в качестве источника данных
 *
 * User: Balovtsev
 * Date: 26.11.2012
 * Time: 16:36:06
 */
public class StoredCardListSource extends CardListSource
{
	public StoredCardListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredCardListSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public StoredCardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override
	protected boolean skipStoredResource(Card card)
	{
		return true;
	}
}
