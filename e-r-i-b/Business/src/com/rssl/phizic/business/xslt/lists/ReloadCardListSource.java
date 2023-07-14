package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonData;

import java.util.List;
import java.util.Map;

/**
 * класс для получения xml-списка карт, который предварительно запрашивает карты из шины
 * @author Pankin
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ReloadCardListSource extends CardListSource
{
	public ReloadCardListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public ReloadCardListSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public ReloadCardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected List<CardLink> getCards(PersonData personData) throws BusinessException, BusinessLogicException
	{
		if (!personData.isWayCardsReloaded() && PersonHelper.isUdboSupported())
			personData.setNeedReloadCards();
		return super.getCards(personData);
	}
}
