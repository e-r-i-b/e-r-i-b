package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.auth.modes.RegistrationMode;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileLoginDataRestriction;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.self.registration.SelfRegistrationConfig;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция начала регистрации мобильного приложения
 * Для регистрации без капчи
 *
 * @author khudyakov
 * @ created 16.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class StartSocialRegistrationOperation extends OperationBase
{
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private AuthParamsContainer container = new AuthParamsContainer();

	public StartSocialRegistrationOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException, BackLogicException, MalformedVersionFormatException
	{
		//данная проверка здесь, а не в форме, только из-за перехода и текстовки
		new MobileLoginDataRestriction().accept(data);

		String login = (String) data.get("login");
		SelfRegistrationConfig selfRegistrationConfig = ConfigFactory.getConfig(SelfRegistrationConfig.class);

		//в «жестком» режиме самостоятельной регистрации запрещена регистрация мобильных приложений по идентификатору iPAS
		boolean registrationIPasAvailable = selfRegistrationConfig.getRegistrationMode() != RegistrationMode.HARD;

		try
		{
			String appType = (String) data.get("appType");
			Document response = CSABackRequestHelper.sendStartSocialRegistrationRq(login, appType, ConfirmStrategyType.sms, registrationIPasAvailable, (String) data.get("extClientID"), (String) data.get("card"), mobilePlatformService.findByPlatformIdIgnoreCase(appType).getPlatformName());

            AuthenticationHelper.fillStartMobileRegistration(container, response);

			String mGUID = container.getParameter(RequestConstants.OUID_TAG);
			if (StringHelper.isEmpty(mGUID))
				throw new ResetMobileGUIDException("Неверная авторизация. Неактивный mGUID.");
		}
		catch (BackException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Получить первоначальные данный для старта регистрации
	 * @return AuthParamsContainer
	 */
	public AuthParamsContainer getAuthParams()
	{
		return container;
	}
}
