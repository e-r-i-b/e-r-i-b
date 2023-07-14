package com.rssl.auth.csamapi.operations;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csamapi.exceptions.RegistrationErrorException;
import com.rssl.auth.csamapi.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileMGUIDDataRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.senders.NotifyFraudMonitoringNewMobileRegistrationSender;
import com.rssl.phizic.rsa.senders.initialization.MobileRegistrationInitializationData;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);
		platformId = (String) data.get("appType");
		password = (String) data.get(Constants.PASSWORD_FIELD);
	}

	protected void checkRestrict(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		new MobileDataCompositeRestriction(new MobileMGUIDDataRestriction(), new MobileVersionDataRestriction()).accept(data);
	}

	protected Document doRequest() throws FrontLogicException, FrontException
	{
		sendFinishMobileRegistrationRequest();
		Document response = sendStartCreateMobileSessionRequest(AuthorizedZoneType.POST_REGISTRATION);
		Element element = response.getDocumentElement();
		LogThreadContext.setDepartmentRegion(XmlHelper.getSimpleElementValue(element, "tb"));
		LogThreadContext.setUserId(XmlHelper.getSimpleElementValue(element, "userId"));
		sendFraudRequest(element);
		return response;
	}

	protected void sendFinishMobileRegistrationRequest() throws FrontException, FrontLogicException
	{
		try
		{
			MobilePlatform platform = mobilePlatformService.findByPlatformId(platformId);
			if (platform == null)
				throw new FrontLogicException(NOT_SUPPORTED_PLATFORM_ERROR);
			CSABackRequestHelper.sendFinishMobileRegistrationRq(guid, password, getDeviceState(platform), deviceId, platform.getPlatformName());
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

	private String getDeviceState(MobilePlatform platform)
	{
		return isLightScheme ? MobileAppScheme.LIGHT.name() : platform.isLightScheme() ? MobileAppScheme.LIGHT.name() : MobileAppScheme.FULL.name();
	}

	/**
	 * Отправить запрос во Fraud.
	 */
	private void sendFraudRequest(Element responseElement)
	{
		try
		{
			if (RSAConfig.getInstance().isSystemActive())
			{
				String versionNumber = XmlHelper.getSimpleElementValue(responseElement, "version");
				String mGUID = XmlHelper.getSimpleElementValue(responseElement, "GUID");
				String csaProfileId = XmlHelper.getSimpleElementValue(responseElement, CSAResponseConstants.PROFILE_ID_TAG);
				//анти-фрод работает только для версии 9.1 и выше.
				if (VersionNumber.fromString(versionNumber).ge(new VersionNumber(9, 1)))
				{
					NotifyFraudMonitoringNewMobileRegistrationSender sender = new NotifyFraudMonitoringNewMobileRegistrationSender();
					sender.initialize(new MobileRegistrationInitializationData(mGUID, csaProfileId));
					sender.send();
				}
			}
		}
		catch (Throwable e)
		{
			log.error(e);
		}
	}
}
