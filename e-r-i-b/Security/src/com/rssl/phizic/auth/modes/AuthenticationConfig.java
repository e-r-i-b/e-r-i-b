package com.rssl.phizic.auth.modes;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;
import java.util.Set;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public abstract class AuthenticationConfig extends Config
{
	protected AuthenticationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return список политик безопасности приложения
	 */
	public abstract List<AccessPolicy> getPolicies();

	/**
	 * Политика для заданного типа доступа
	 * @param accessType тип доступа (secure==защищенный, simple==упрощенный)
	 * @return политика
	 */
	public abstract AccessPolicy getPolicy(AccessType accessType);

	/**
	 * @return набор типов доступа
	 */
	public abstract Set<AccessType> getAccessTypes();
}
