package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.login.exceptions.WrongCodeConfirmException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DocumentConfig;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция подтверждения регистрации и входа в mAPI
 *
 * @author khudyakov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileAppRegistrationOperation extends MobileAuthOperationBase implements GetAuthDataOperation
{
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private AuthData authData = new AuthData();

	public MobileAppRegistrationOperation(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		//данная проверка здесь, а не в форме, только из-за перехода и текстовки
		new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction(), new MobileVersionDataRestriction()).accept(data);

		String mGUID = (String) data.get("mGUID");
		boolean ligthAppScheme = LoginHelper.isLigthAppScheme((String) data.get("isLightScheme"));
		String platformId = (String) data.get("appType");

		MobilePlatform platform = mobilePlatformService.findByPlatformId(platformId);
		if (platform == null)
			throw new BusinessLogicException(NOT_SUPPORTED_PLATFORM_ERROR);

		// 1. Подтверждаем операцию создания mGUID.
		confirmReg(data, mGUID);

		// 2. завершаем регистрацию мобильного приложения
		try
		{
			CSABackRequestHelper.sendFinishMobileRegistrationRq(mGUID, (String) data.get("password"), ligthAppScheme ? MobileAppScheme.LIGHT.name() : MobileAppScheme.FULL.name(), (String) data.get("devID"), platform.getPlatformName());
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
			boolean isLightScheme = false;
			if (platform.isLightScheme())
				isLightScheme = true;
			Document response = createMobileSession(mGUID, ligthAppScheme ? MobileAppScheme.LIGHT.name() : null, (String) data.get(Constants.DEVICE_ID_FIELD), (String) data.get("version"), AuthorizedZoneType.POST_REGISTRATION);
			AuthenticationHelper.fillFromERIBCSAData(authData, response, isLightScheme, null);

			if (LoginType.MAPI != authData.getLoginType())
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
