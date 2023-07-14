/**
 * User: usachev
 * Date: 09.12.14
 */
package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.context.PersonContext;

public class FundRequestRestrictionOnUser implements FundRequestRestriction
{
	public boolean accept(FundRequest fundRequest)
	{
		Long id = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		return id.equals(fundRequest.getLoginId());
	}
}
