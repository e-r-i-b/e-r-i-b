package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.utils.SynchKeyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCacheKeyComposer implements SessionCacheKeyComposer
{
	public List<String> getSessionKeys(Object object) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return null;
		
		if (!(object instanceof Loan))
			throw new BusinessException("Ошибка в LoanCacheKeyComposer: Ожидался Loan");

		Loan loan = (Loan) object;

		List<Long> loginIds = SessionComposerQueryHelper.getLoginIdsOfOwners(LoanLink.class, loan.getAccountNumber());
		if (loginIds == null || loginIds.isEmpty())
			throw new BusinessException("Для кредита с номером " + loan.getAccountNumber() + " не найдены владельцы");

		List<String> result = new ArrayList<String>();
		for (Long id : loginIds)
			result.add(XmlEntityListCacheSingleton.SESSION_LISTS_KEY + SynchKeyUtils.makeClientSynchKey(id));

		return result;
	}

	public String getKey(Object object)
	{
		return null;
	}
}