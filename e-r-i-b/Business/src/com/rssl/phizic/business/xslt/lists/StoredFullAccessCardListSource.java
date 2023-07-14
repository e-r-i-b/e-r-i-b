package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 27.11.2012
 * Time: 14:06:18
 */
public class StoredFullAccessCardListSource extends FullAccessCardListSource
{
	public StoredFullAccessCardListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredFullAccessCardListSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public StoredFullAccessCardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override
	protected boolean skipStoredResource(Card card)
	{
		return true;
	}
}
