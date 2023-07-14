package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.gate.depo.DepoAccount;
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

public class DepoAccountCacheKeyComposer implements SessionCacheKeyComposer
{
	public List<String> getSessionKeys(Object object) throws BusinessException
	{
		if (!(object instanceof DepoAccount))
			throw new BusinessException("Ошибка в DepoAccountCacheKeyComposer: Ожидался DepoAccount");

		List<String> result = new ArrayList<String>();
		DepoAccount depoAccount = (DepoAccount) object;
		String clientSynchKey = SynchKeyUtils.makeClientSynchKey(ESBHelper.parseLoginId(depoAccount.getId()));
		result.add(XmlEntityListCacheSingleton.SESSION_LISTS_KEY + clientSynchKey);
		return result;
	}

	public String getKey(Object object)
	{
		return null;
	}
}