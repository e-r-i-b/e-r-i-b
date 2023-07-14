package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountPseudonym extends Pseudonym
{
	private AccountLink accountLink;

	public ExternalResourceLink getLink() throws BusinessException, BusinessLogicException
	{
		if (accountLink == null)
		{
			List<AccountLink> links = PersonContext.getPersonDataProvider().getPersonData().getAccounts();
			for (AccountLink aLink : links)
			{
				if (aLink.getExternalId().equals(getValue()))
				{
					accountLink = aLink;
					break;
				}
			}
		}
		return accountLink;
	}

	public String getNumber() throws BusinessException, BusinessLogicException
	{
		if (accountLink == null)
		{
			getLink();
		}
		return accountLink.getNumber();
	}
}
