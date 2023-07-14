package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountCacheKeyComposer implements SessionCacheKeyComposer
{
	public List<String> getSessionKeys(Object object) throws BusinessException
	{
		if (!(object instanceof Account))
			throw new BusinessException("������ � AccountCacheKeyComposer: �������� Account");

		//���� ����� ���� ������ ����, ������� ���� ����� ������ �� ����������� ����������
		// �� ����� ����� ������������� ������������ �� ���������
		if (PersonContext.isAvailable())
		{
			List<String> result = new ArrayList<String>();
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			result.add(XmlEntityListCacheSingleton.SESSION_LISTS_KEY + personData.createDocumentOwner().getSynchKey());
			return result;
		}

		//���������� �� ����������, ������ �� ������.
		return null;
	}

	public String getKey(Object object)
	{
		return null;
	}
}