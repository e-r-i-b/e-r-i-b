package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ConnectorBlockedException;
import com.rssl.auth.csa.wsclient.exceptions.RestrictionViolatedException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.exceptions.RegistrationErrorException;
import com.rssl.phizic.business.login.exceptions.ResetMobileGUIDException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.phizic.business.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция получения данный аутентификации мобильного приложения из ЕРИБ ЦСА
 *
 * @author khudyakov
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetAuthDataSimpleMobileClientFromERIBCSAOperation extends MobileAuthOperationBase implements GetAuthDataOperation
{
	private AuthData authData = new AuthData();
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	public GetAuthDataSimpleMobileClientFromERIBCSAOperation(Map<String, Object> data) throws BusinessLogicException, BusinessException
	{
		try
		{
			//данная проверка здесь, а не в форме, только из-за перехода и текстовки
			new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction(), new MobileVersionDataRestriction()).accept(data);

			String mGUID = (String) data.get("mGUID");
			String appScheme = LoginHelper.isLigthAppScheme((String) data.get("isLightScheme")) ? MobileAppScheme.LIGHT.name() : null;
			String deviceId = (String) data.get("devID");
			String version = (String) data.get("version");

			Document response = createMobileSession(mGUID, appScheme, deviceId, version, AuthorizedZoneType.PRE_AUTHORIZATION);

			boolean isLightScheme = false;
			String platformName = (String) data.get("appType");
			if (!StringHelper.isEmpty(platformName))
			{
				MobilePlatform platform = mobilePlatformService.findByPlatformId(platformName);
				if (platform == null)
					throw new BusinessLogicException(NOT_SUPPORTED_PLATFORM_ERROR);
				if (platform.isLightScheme())
					isLightScheme = true;
			}
			AuthenticationHelper.fillFromERIBCSAData(authData, response, isLightScheme, platformName);

			if (LoginType.MAPI != authData.getLoginType())
			{
				throw new BusinessException("Вход в мобильной версии возможен только через mAPI коннектор, используется тип " + authData.getLoginType());
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
