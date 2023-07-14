package com.rssl.phizic.business.smsbanking.resolver;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;
import com.rssl.phizic.business.smsbanking.pseudonyms.DepositPseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class DepositResolver implements PseudonymResolver
{
	public ExternalResourceLinkBase getLink(Pseudonym pseudonym) throws BusinessException, BusinessLogicException
	{
		List<DepositLink> list = PersonContext.getPersonDataProvider().getPersonData().getDeposits();
		for (DepositLink link : list)
		{
			if (link.getExternalId().equals(pseudonym.getValue()))
			{
				return link;
			}
		}
		return null;
	}

	public Class<? extends Pseudonym> getPseudonymClass()
	{
		return DepositPseudonym.class;
	}
}
