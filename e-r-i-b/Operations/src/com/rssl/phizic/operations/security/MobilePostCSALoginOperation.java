package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 05.08.13
 * @ $Author$
 * @ $Revision$
 *
 * операция аутентификации пользователя МАПИ
 */
public class MobilePostCSALoginOperation extends MobileAuthOperationBase
{
	private AuthData authData;
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	/**
	 * конструктор
	 * @param token токен аутентификации
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public MobilePostCSALoginOperation(String token) throws BusinessException, BusinessLogicException
	{
		try
		{
			authData = new AuthData();
			Document response = CSABackRequestHelper.sendFinishCreateMobileSessionRq(token);
			AuthenticationHelper.fillFromMAPICSAData(authData, response);
			updateAuthData();
			if (LoginType.MAPI != authData.getLoginType())
			{
				throw new BusinessException("Вход в мобильной версии возможен только через mAPI коннектор, используется тип " + authData.getLoginType().name());
			}

			if (authData.getMobileAppScheme() == null)
			{
				throw new RegistrationErrorException();
			}
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	/**
	 * @return данные аутентификации
	 */
	public AuthData getAuthData()
	{
		return authData;
	}

	/**
	 * Обновление контекста аутентификаци
	 * @throws BusinessException
	 */
	public void updateAuthenticationContext() throws BusinessException
	{
		AuthenticationContext context = AuthenticationContext.getContext();
		if (context == null || authData == null)
			return;

		String appType = authData.getDeviceInfo();

		if (StringHelper.isNotEmpty(appType))
		{
			MobilePlatform mobilePlatform = mobilePlatformService.findByPlatformIdIgnoreCase(appType);
			boolean needPasswordConfirm = (mobilePlatform != null && mobilePlatform.isPasswordConfirm());
			context.setPlatformPasswordConfirm(needPasswordConfirm);
		}

		context.setAuthorizedZoneType(authData.getAuthorizedZoneType());
	}

	/**
	 * Обновление контекста аутентификаци
	 * @throws BusinessException
	 */
	public void updateAuthData() throws BusinessException
	{
		if (authData == null)
			return;

		String appType = authData.getDeviceInfo();

		if (StringHelper.isNotEmpty(appType))
		{
			MobilePlatform mobilePlatform = mobilePlatformService.findByPlatformIdIgnoreCase(appType);
			if (authData.getMobileAppScheme().equals(MobileAppScheme.FULL))
			{
				authData.setMobileAppScheme(mobilePlatform.isLightScheme() ? MobileAppScheme.LIGHT : MobileAppScheme.FULL);
			}
		}
	}
}
