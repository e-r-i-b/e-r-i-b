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
 * ������ ��� �������������� �������� �������������.
 */
public class UpdatePersonDataOperation
{
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private AuthenticationContext authContext;
	private UserConfirmStrategySettings userConfirmStrategySettings;

	/**
	 * �������������� ������
	 * @param authContext �������� ��������������
	 * @param userConfirmStrategySettings ��������� ������������� � �������.
	 */
	public void initialize(AuthenticationContext authContext, UserConfirmStrategySettings userConfirmStrategySettings)
	{
		this.authContext = authContext;
		this.userConfirmStrategySettings = userConfirmStrategySettings;
	}

	/**
	 * ��������������� �������� �������������
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

		// ��������� ���� ������������� ��������
		userProperties.setProperty("userOptionType", userConfirmStrategySettings.getUserConfirmType());

		//� auth-��������� ������ ������ ���������� ������
		authContext.getPolicyProperties().setProperty("userOptionType", userConfirmStrategySettings.getUserConfirmType());
		authContext.getPolicyProperties().setProperty(authChoice.getProperty(), userConfirmStrategySettings.getAuthConfirmType());
		// ��������� ���������� �������� ������������
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
			throw new BusinessException("�� ������� ���������������� �������� ������������� ��� ������� �������� �������");
		return userProperties;
	}
}
