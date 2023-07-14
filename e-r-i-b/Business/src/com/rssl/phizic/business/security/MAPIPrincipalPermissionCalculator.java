package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * PrincipalPermissionCalculator для mAPI
 *
 * @author khudyakov
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class MAPIPrincipalPermissionCalculator extends PrincipalPermissionCalculator
{
    private static final String MOBILE_BLOCKING_FULL_SCHEME_KEY     = "mobile-blocking-full-scheme";    //ключ схемы заблокированных услуг в mAPI
    private static final String MOBILE_BLOCKING_LIGHT_SCHEME_KEY    = "mobile-blocking-light-scheme";

	public MAPIPrincipalPermissionCalculator(UserPrincipal principal) throws BusinessException
	{
		super(principal);
	}

	Set<String> getBlockedServices()
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		SharedAccessScheme blockScheme = schemesConfig.getByCode(getPrincipal().isMobileLightScheme() ? MOBILE_BLOCKING_LIGHT_SCHEME_KEY : MOBILE_BLOCKING_FULL_SCHEME_KEY);
		if (blockScheme == null)
			return Collections.emptySet();

		if (CollectionUtils.isEmpty(blockScheme.getServices()))
			return Collections.emptySet();

		Set<String> services = new HashSet<String>();
		for (Service blockService : blockScheme.getServices())
		{
			services.add(blockService.getKey());
		}
		return services;
	}
}
