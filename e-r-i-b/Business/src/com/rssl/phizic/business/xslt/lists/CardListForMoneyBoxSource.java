package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.comparator.CardAmountComparator;

import java.util.Comparator;
import java.util.Map;

/**
 * @author vagin
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 * Сорс с дополнительной информацией по подключению карты к мобильному банку.
 */
public class CardListForMoneyBoxSource extends CardListSource
{
	public CardListForMoneyBoxSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public CardListForMoneyBoxSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public CardListForMoneyBoxSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected void addMobileBankRegistrationInfo(EntityListBuilder builder, CardLink cardLink)
	{
		builder.appentField("hasMBRegistration", String.valueOf(MobileBankManager.hasAnyMB(cardLink.getNumber())));
	}
}
