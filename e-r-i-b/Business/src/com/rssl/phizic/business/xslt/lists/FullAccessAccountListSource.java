package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;

import java.util.List;
import java.util.Map;

/**
 * @author: vagin
 * @ created: 26.03.2012
 * @ $Author$
 * @ $Revision$
 * Лист сорс для счетов без учета их видимости в системе
 */
public class FullAccessAccountListSource extends AccountListSource
{
	protected static final ExternalResourceService resourceService = new ExternalResourceService();

	public FullAccessAccountListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public FullAccessAccountListSource(EntityListDefinition definition, AccountFilter accountFilter)
	{
		super(definition, accountFilter);
	}

	public FullAccessAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected List<AccountLink> getAccountsList() throws BusinessException, BusinessLogicException
    {
		return resourceService.getLinks(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(),AccountLink.class);
    }
}
