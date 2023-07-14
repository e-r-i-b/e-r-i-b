package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;

/**
 * @author niculichev
 * @ created 11.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestPrincipalPermissionCalculator extends PrincipalPermissionCalculator
{
	private static final String CACHE_KEY = "DEFAULT_GUEST_SCHEME";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Cache<String, AccessScheme> CACHE = new Cache<String, AccessScheme>(new OnCacheOutOfDateListener<String, AccessScheme>()
	{
		public AccessScheme onRefresh(String key)
		{
			try
			{
				return defaultAccessSchemeService.findByCreationTypeAndDepartment(DefaultSchemeType.GUEST, null);
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при получении дефолтной гостевой схемы.", e);
				return null;
			}
		}
	}, 15L);

	public GuestPrincipalPermissionCalculator(UserPrincipal principal) throws BusinessException
	{
		super(principal);
	}

	protected AccessScheme getAccessScheme(UserPrincipal principal, AuthenticationContext context) throws BusinessException
	{
		return CACHE.getValue(CACHE_KEY);
	}
}
