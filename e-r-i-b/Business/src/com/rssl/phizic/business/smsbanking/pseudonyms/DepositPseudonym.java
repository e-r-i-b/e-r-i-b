package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.resources.external.DepositLink;
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
public class DepositPseudonym extends Pseudonym
{
	private DepositLink depositLink;

	public ExternalResourceLink getLink() throws BusinessException, BusinessLogicException
	{
		if (depositLink == null)
		{
			List<DepositLink> links = PersonContext.getPersonDataProvider().getPersonData().getDeposits();
			for (DepositLink dLink : links)
			{
				if (dLink.getExternalId().equals(getValue()))
				{
					depositLink = dLink;
					break;
				}
			}
		}
		return depositLink;
	}

	public String getNumber() throws BusinessException, BusinessLogicException
	{
		if (depositLink == null)
		{
			getLink();
		}
		return depositLink.getDepositInfo().getAccount().getNumber();
	}
}
