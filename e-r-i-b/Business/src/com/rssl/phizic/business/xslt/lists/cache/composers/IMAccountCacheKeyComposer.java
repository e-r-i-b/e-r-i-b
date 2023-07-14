package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.utils.SynchKeyUtils;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountCacheKeyComposer implements SessionCacheKeyComposer
{
	public List<String> getSessionKeys(Object object) throws BusinessException
	{
		if (!(object instanceof IMAccount))
			throw new BusinessException("Ошибка в IMAccountCacheKeyComposer: Ожидался Loan");

		List<String> result = new ArrayList<String>();
		IMAccount imAccount = (IMAccount) object;
		String clientSynchKey = SynchKeyUtils.makeClientSynchKey(ESBHelper.parseLoginId(imAccount.getId()));
		result.add(XmlEntityListCacheSingleton.SESSION_LISTS_KEY + clientSynchKey);
		return result;
	}

	public String getKey(Object object)
	{
		return null;
	}
}