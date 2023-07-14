package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.login.exceptions.WrongCodeConfirmException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DocumentConfig;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция подтверждения регистрации и входа в socialAPI
 *
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class SocialAppRegistrationOperation extends SocialAuthOperationBase implements GetAuthDataOperation
{
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private AuthData authData = new AuthData();

	public SocialAppRegistrationOperation(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		//данная проверка здесь, а не в форме, только из-за перехода и текстовки
		new MobileMGUIDDataRestriction().accept(data);

		String mGUID = (String) data.get("mGUID");
		String platformId = (String) data.get("appType");

		MobilePlatform platform = mobilePlatformService.findByPlatformId(platformId);
		if (platform == null)
			throw new BusinessLogicException(NOT_SUPPORTED_PLATFORM_ERROR);

		// 1. Подтверждаем операцию создания mGUID.
		confirmReg(data, mGUID);

		// 2. завершаем регистрацию мобильного приложения
		try
		{
			CSABackRequestHelper.sendFinishSocialRegistrationRq(mGUID, (String) data.get("password"), MobileAppScheme.FULL.name(), (String) data.get("devID"), platform.getPlatformName());
		}
		catch (FailureIdentificationException e)
		{
			throw new RegistrationErrorException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}

		//TODO: Сохранять в контексте аутентификации и сбрасывать мобильный кошелек в 0, огда будет реализован
		//boolean isNewPassword = Boolean.parseBoolean(
		//		XmlHelper.getSimpleElementValue(responseData.getDocumentElement(), RequestConstants.IS_NEW_PASSWORD_TAG));

		// 3. начинаем сессию по только что полученной регистрации
		try
		{
			Document response = createSocialSession(mGUID, null, (String) data.get(Constants.DEVICE_ID_FIELD), AuthorizedZoneType.POST_REGISTRATION);
			AuthenticationHelper.fillFromERIBCSAData(authData, response, false, null);

			if (LoginType.SOCIAL != authData.getLoginType())
			{
				throw new BusinessException("Вход в мобильной версии возможен только через mAPI коннектор, используется тип " + authData.getLoginType().name());
			}

			if (authData.getMobileAppScheme() == null)
			{
				throw new RegistrationErrorException();
			}
		}
		catch (ConnectorBlockedException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (RestrictionViolatedException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new ResetMobileGUIDException(e.getMessage(), e);
		}
	}

	protected void confirmReg(Map<String, Object> data, String mGUID) throws BusinessLogicException, BusinessException
	{
		try
		{
			CSABackRequestHelper.sendConfirmOperationRq(mGUID, (String) data.get("smsPassword"));
		}
		catch (InvalidCodeConfirmException e)
		{
			throw new WrongCodeConfirmException(ConfigFactory.getConfig(DocumentConfig.class).getInvalidConfirmCodeRequest(), e.getTime(), e.getAttempts(), e);
		}
		catch (FailureIdentificationException e)
		{
			throw new RegistrationErrorException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	public AuthData getAuthData()
	{
		return authData;
	}
}
