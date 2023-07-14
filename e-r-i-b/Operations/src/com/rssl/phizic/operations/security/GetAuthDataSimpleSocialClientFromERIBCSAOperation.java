package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ConnectorBlockedException;
import com.rssl.auth.csa.wsclient.exceptions.RestrictionViolatedException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция получения данный аутентификации социального приложения из ЕРИБ ЦСА
 *
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class GetAuthDataSimpleSocialClientFromERIBCSAOperation extends SocialAuthOperationBase implements GetAuthDataOperation
{
	private AuthData authData = new AuthData();
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	public GetAuthDataSimpleSocialClientFromERIBCSAOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		try
		{
			//данная проверка здесь, а не в форме, только из-за перехода и текстовки
			new MobileMGUIDDataRestriction().accept(data);

			String mGUID = (String) data.get("mGUID");
			String deviceId = (String) data.get("extClientID");

			Document response = createSocialSession(mGUID, null, deviceId, AuthorizedZoneType.PRE_AUTHORIZATION);

			String platformName = (String) data.get("appType");
			if (!StringHelper.isEmpty(platformName))
			{
				MobilePlatform platform = mobilePlatformService.findByPlatformId(platformName);
				if (platform == null)
					throw new BusinessLogicException(NOT_SUPPORTED_PLATFORM_ERROR);
			}
			AuthenticationHelper.fillFromERIBCSAData(authData, response, false, platformName);

			if (LoginType.SOCIAL != authData.getLoginType())
			{
				throw new BusinessException("Вход в социальной версии возможен только через socialAPI коннектор, используется тип " + authData.getLoginType());
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

	public AuthData getAuthData()
	{
		return authData;
	}
}
