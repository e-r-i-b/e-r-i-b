package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Список карт без карты, на которую выплачиваются проценты
 * @author Jatsky
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public class CardListSourceWithoutInterest extends CardListSource
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final String ACCOUNT_ID = "accountId";

	public CardListSourceWithoutInterest(EntityListDefinition definition)
	{
		super(definition);
	}

	public CardListSourceWithoutInterest(EntityListDefinition definition, CardFilter cardFilter)
	{
		super(definition, cardFilter);
	}

	public CardListSourceWithoutInterest(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	@Override protected boolean isInterestCard(String cardNum, Map<String, String> params) throws BusinessException
	{
		String accountId = params.get(ACCOUNT_ID);
		if (!StringHelper.isEmpty(accountId))
		{
			AccountLink accountLink = externalResourceService.findLinkById(AccountLink.class, AccountLink.getIdFromCode(accountId));
			return StringHelper.equalsNullIgnore(accountLink.getAccount().getInterestTransferCard(), cardNum);
		}
		return super.isInterestCard(cardNum, params);
	}
}
