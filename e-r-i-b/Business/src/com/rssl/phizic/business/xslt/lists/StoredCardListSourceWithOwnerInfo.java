package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * Разрешает использование закэшированных карт в платежах
 * @author Pankin
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class StoredCardListSourceWithOwnerInfo extends CardListSourceWithOwnerInfo
{
	public StoredCardListSourceWithOwnerInfo(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredCardListSourceWithOwnerInfo(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public StoredCardListSourceWithOwnerInfo(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected boolean skipStoredResource(Card card)
	{
		return true;
	}
}
