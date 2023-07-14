package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.loanclaim.officeClaim.OfficeLoanClaimCaheService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils;
import net.sf.ehcache.Cache;

/**
 * @author Erkin
 * @ created 09.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Очистка кешей, связанных с клиентом
 */
public class UserClearCacheOperation
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private CommonLogin login;

	public void initialize(CommonLogin login)
	{
		this.login = login;
	}

	protected CommonLogin getLogin()
	{
		return login;
	}

	public void clearCache()
	{
		resetInformCardsCache();
		resetOfficeLoanClaimCache();
	}

	private void resetInformCardsCache()
	{
		Cache cache = CacheProvider.getCache(MobileBankUtils.INFO_CARDS_PHONES_CACHE);
		if (cache != null)
		{
			cache.remove(MobileBankUtils.getInfoCardsPhonesCacheKey(login, true));
			cache.remove(MobileBankUtils.getInfoCardsPhonesCacheKey(login, false));
		}
	}

	private void resetOfficeLoanClaimCache()
	{
		OfficeLoanClaimCaheService.doClearCache(login.getId().toString());
	}
}
