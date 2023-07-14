package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.ManualPasswordGenerator;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kosyakov
 * @ created 12.01.2006
 * @ $Author: jatsky $
 * @ $Revision: 62737 $
 */
public class ClientLoginGenerator extends LoginGeneratorBase<Login>
{
	private static final MultiInstanceSecurityService securityService = new MultiInstanceSecurityService();

	/**
	 * Генерация поля user_id в таблице LOGINS, для клиентов v6.
	 * Служит для отбора и просмотра записей клиентов в админке. 
	 */
	private static final String USER_ID = " ";

	public ClientLoginGenerator(String instanceName)
	{
		this(USER_ID, null, instanceName);
	}

	public ClientLoginGenerator(String userId, String password, String instanceName)
	{
		super(new ManualUserIdValueGenerator(userId), new ManualPasswordGenerator(password), instanceName);
	}

	protected LoginBase newInstance()
	{
		return new LoginImpl();
	}

	protected AuthenticationConfig getAuthenticationConfig()
	{
		return ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
	}

	protected void checkDublicates(String userId) throws SecurityDbException, DublicateUserIdException
	{
		if ( !USER_ID.equals(userId) && securityService.getClientLogin(userId,instanceName) != null)
			throw new DublicateUserIdException(userId, "");
	}

	protected void enableDefaultAccess(CommonLogin login) throws SecurityDbException, SecurityLogicException
	{

		AuthenticationConfig authenticationConfig = getAuthenticationConfig();
		Set<AccessType> accessTypes = new HashSet<AccessType>(authenticationConfig.getAccessTypes());
		// добавляем ограниченный доступ для мобильных приложений независимо от того, каким приложением
		// воспользовался клиент при добавлении его в ЕРИБ
		accessTypes.add(AccessType.mobileLimited);

		for (AccessType accessType : accessTypes)
		{
			Properties properties = accessModeService.getTemplateProperties(accessType, instanceName);

			if(properties != null)
				accessModeService.enableAccess(login, accessType, properties, instanceName);
		}

	}

	protected boolean needSavePassword()
	{
		return false;
	}
}
