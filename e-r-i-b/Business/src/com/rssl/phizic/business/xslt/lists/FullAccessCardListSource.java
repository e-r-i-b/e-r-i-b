package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonData;

import java.util.List;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 26.03.2012
 * @ $Author$
 * @ $Revision$
 * Сорс для получения справочника всех карт(без учета видимости в системе)
 */
public class FullAccessCardListSource extends CardListSource
{
	protected static final ExternalResourceService resourceService = new ExternalResourceService();

	public FullAccessCardListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public FullAccessCardListSource(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public FullAccessCardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected List<CardLink> getCards(PersonData personData) throws BusinessException, BusinessLogicException
	{
		return resourceService.getLinks(personData.getPerson().getLogin(),CardLink.class);
	}
}
