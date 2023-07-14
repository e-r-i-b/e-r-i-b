package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 18.04.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class EditPolicyTemplateOperation extends OperationBase
{
	private static final AccessPolicyService accessPolicyService = new AccessPolicyService();

	private AccessType   accessType;
	private AccessPolicy policy;
	private Properties   properties;
	private boolean      enabled;

	/**
	 * »нициализаци€
	 * @param accessType тип доступа дл€ которого настраиваетс€ политика
	 */
	public void initialize(AccessType accessType) throws BusinessException
	{
		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class, accessType.getScope().equals(SecurityService.SCOPE_CLIENT) ? Application.PhizIC : Application.PhizIA);

		this.accessType = accessType;
		this.policy     = config.getPolicy(accessType);
		
		try
		{
			Properties temp = accessPolicyService.getTemplateProperties(accessType);
			enabled = (temp != null);
			if(temp == null)
				temp = new Properties();

			properties = temp;
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return настраиваема€ политика. ≈сли ==null то политика дл€ этого типа доступа отсутствует
	 */
	public AccessPolicy getPolicy()
	{
		return policy;
	}

	/**
	 * @return текущие настройки
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * ”становить новое значение
	 * @param name им€ свойства
	 * @param value значение
	 */
	public void setProperty(String name, String value)
	{
		properties.setProperty(name, value);
	}

	/**
	 * @return политика разрешена
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * @param enabled политика разрешена
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * —охранить изменени€
	 */
	public void save() throws BusinessException
	{

		if(policy == null)
			throw new BusinessException("Ќевозможно настроить шаблон дл€ отсутствующей политики. “ип доступа " + accessType);

		try
		{
			if(enabled)
			{
				accessPolicyService.enableTemplateAccess(accessType, properties);
			}
			else
			{
				accessPolicyService.disableTemplateAccess(accessType);
			}
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}
}