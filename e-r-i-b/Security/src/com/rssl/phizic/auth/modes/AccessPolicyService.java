package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: krenev_a $
 * @ $Revision: 48606 $
 */

public class AccessPolicyService extends MultiInstanceAccessPolicyService
{
	private static final String USER_OPTION_TYPE = "userOptionType";

	public void disableAccess(final CommonLogin login, final AccessType accessType) throws SecurityDbException
	{
		super.disableAccess(login, accessType, null);
	}

	public void disableTemplateAccess(final AccessType accessType) throws SecurityDbException
	{
		super.disableTemplateAccess(accessType, null);
	}

	public void enableAccess(final CommonLogin login, final AccessType accessType, final Properties properties) throws SecurityDbException
	{
		super.enableAccess(login, accessType, properties, null);
	}

	public void enableTemplateAccess(final AccessType accessType, final Properties properties) throws SecurityDbException
	{
		super.enableTemplateAccess(accessType, properties, null);
	}

	public Properties getProperties(CommonLogin login, AccessType accessType) throws SecurityDbException
	{
		return super.getProperties(login, accessType, null);
	}

	public Properties getTemplateProperties(AccessType accessType) throws SecurityDbException
	{
		return super.getTemplateProperties(accessType, null);
	}

	public boolean isAccessTypeAllowed(CommonLogin login, AccessType accessType) throws SecurityDbException
	{
		return super.isAccessTypeAllowed(login, accessType, null);    
	}

	public void removeAllLoginSettings(final CommonLogin login,final String instanceName) throws SecurityDbException
	{
		super.removeAllLoginSettings(login, null);
	}

	/**
	 * ѕолучить предпочтительный способ подтверждени€,
	 * @param login логин клиента
	 * @param accessType тип доступа клиента
	 * @return предпочтительный способ подтверждени€
	 * @throws SecurityDbException
	 */
	public ConfirmStrategyType getUserOptionType(CommonLogin login, AccessType accessType) throws SecurityDbException
	{
		Properties properties = getProperties(login, accessType);
		if (properties == null)
			return null;

		String name = properties.getProperty(USER_OPTION_TYPE);
		return StringHelper.isEmpty(name)? null: ConfirmStrategyType.valueOf(name);
	}
}