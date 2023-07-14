package com.rssl.auth.csasocial.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csasocial.exceptions.RegistrationErrorException;
import com.rssl.auth.csasocial.operations.restrictions.SocialDataCompositeRestriction;
import com.rssl.auth.csasocial.operations.restrictions.SocialMGUIDDataRestriction;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.mobile.Constants;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 04.08.13
 * @ $Author$
 * @ $Revision$
 *
 * операция завершения регистрации
 */
public class FinishRegistrationOperation extends LoginOperation
{
	private String password;
	private String platformId;
	private MobilePlatformService mobilePlatformService = new MobilePlatformService();
	protected static final String NOT_SUPPORTED_PLATFORM_ERROR = "Вы не можете войти в приложение. Пожалуйста, обратитесь в Контактный Центр по телефону 8 800 555 5550.";

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);
		platformId = (String) data.get("appType");
		password   = (String) data.get(Constants.PASSWORD_FIELD);
	}

	protected void checkRestrict(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		new SocialDataCompositeRestriction(new SocialMGUIDDataRestriction()).accept(data);
	}

	protected Document doRequest() throws FrontLogicException, FrontException
	{
        sendFinishSocialRegistrationRequest();

		return sendStartCreateSocialSessionRequest(AuthorizedZoneType.POST_REGISTRATION);
	}

	protected void sendFinishSocialRegistrationRequest() throws FrontException, FrontLogicException
	{
		try
		{
			MobilePlatform platform = mobilePlatformService.findByPlatformId(platformId);
			if (platform == null)
			{
				throw new FrontLogicException(NOT_SUPPORTED_PLATFORM_ERROR);
			}

			CSABackRequestHelper.sendFinishSocialRegistrationRq(guid, password, MobileAppScheme.FULL.name(), clientId, platform.getPlatformName());
		}
		catch (FailureIdentificationException e)
		{
			throw new RegistrationErrorException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
		catch(BackLogicException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			throw new FrontException(e);
		}
	}
}
