package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.UserConfirmStrategySettings;
import com.rssl.phizic.logging.settings.NotificationInputType;
import com.rssl.phizic.logging.settings.UserNotificationLogHelper;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Properties;

/**
 * @author komarov
 * @ created 30.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для восстановления настроек подтверждения.
 */
public class UpdatePersonDataOperation
{
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private AuthenticationContext authContext;
	private UserConfirmStrategySettings userConfirmStrategySettings;

	/**
	 * Инициализирует хелпер
	 * @param authContext контекст аутентификации
	 * @param userConfirmStrategySettings настройки подтверждений в системе.
	 */
	public void initialize(AuthenticationContext authContext, UserConfirmStrategySettings userConfirmStrategySettings)
	{
		this.authContext = authContext;
		this.userConfirmStrategySettings = userConfirmStrategySettings;
	}

	/**
	 * восстановливает настроек подтверждения
	 * @throws BusinessException
	 */
	public void updateConfirmationSettings() throws BusinessException
	{

		AccessPolicy policy = authContext.getPolicy();
		AccessType accessType = policy.getAccessType();
		CommonLogin login = authContext.getLogin();

		Properties userProperties = getUserProperties(accessType, login);

		Choice authChoice = policy.getAuthenticationChoice();

		userProperties.setProperty(authChoice.getProperty(), userConfirmStrategySettings.getAuthConfirmType());

		// Установка типа подтверждения операции
		userProperties.setProperty("userOptionType", userConfirmStrategySettings.getUserConfirmType());

		//В auth-контексте должны лежать актуальные данные
		authContext.getPolicyProperties().setProperty("userOptionType", userConfirmStrategySettings.getUserConfirmType());
		authContext.getPolicyProperties().setProperty(authChoice.getProperty(), userConfirmStrategySettings.getAuthConfirmType());
		// Сохраняем обновлённые свойства безопасности
		try
		{
			accessService.enableAccess(login, accessType, userProperties);
			UserNotificationLogHelper.saveNotificationSettings(login.getId(), NotificationInputType.operationConfirm, userConfirmStrategySettings.getUserConfirmType());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	private Properties getUserProperties(AccessType accessType, CommonLogin login) throws BusinessException
	{
		Properties userProperties = null;

		try
		{
			userProperties = accessService.getProperties(login, accessType);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		if (userProperties == null)
			throw new BusinessException("Не найдено пользовательских настроек подтверждения для текущей политики доступа");
		return userProperties;
	}
}
