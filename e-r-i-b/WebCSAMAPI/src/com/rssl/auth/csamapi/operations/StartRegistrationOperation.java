package com.rssl.auth.csamapi.operations;

import com.rssl.auth.csa.front.exceptions.SendSmsMessageFrontException;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.auth.csamapi.exceptions.FailureIdentificationFrontException;
import com.rssl.auth.csamapi.exceptions.ResetMobileGUIDException;
import com.rssl.auth.csamapi.operations.restrictions.MobileDataCompositeRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileLoginDataRestriction;
import com.rssl.auth.csamapi.operations.restrictions.MobileVersionDataRestriction;
import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.InterchangeCSABackOperationBase;
import com.rssl.phizic.auth.modes.RegistrationMode;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.restrictions.MobileRegistrationRestriction;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.CSAMAPIConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 25.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция начала регистрации мобильного приложения
 */
public class StartRegistrationOperation extends InterchangeCSABackOperationBase
{
	private String login;
	private String appType;
	private String version;
	private String devId;
    private String card;
    private MobilePlatformService mobilePlatformService = new MobilePlatformService();

	private AuthParamsContainer container;

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);

		login = (String) data.get(Constants.LOGIN_FIELD);
		appType = (String) data.get(Constants.APP_TYPE_FIELD);
		version = (String) data.get(Constants.VERSION_FIELD);
		devId = (String) data.get(Constants.DEVICE_ID_FIELD);
        card = (String) data.get(Constants.CARD_FIELD);
		container = new AuthParamsContainer();
	}

	/**
	 * @return контейнер параметров
	 */
	public AuthParamsContainer getAuthParams()
	{
		return container;
	}

	protected void checkRestrict(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		new MobileDataCompositeRestriction(new MobileVersionDataRestriction(), new MobileLoginDataRestriction()).accept(data);
		try
		{
			new MobileRegistrationRestriction().accept(data);
		}
		catch (BusinessException e)
		{
			throw new FrontException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new FrontLogicException(e);
		}
	}

	protected Document doRequest() throws FrontLogicException, FrontException
	{
		CSAMAPIConfig config = ConfigFactory.getConfig(CSAMAPIConfig.class);
		//в «жестком» режиме самостоятельной регистрации запрещена регистрация мобильных приложений по идентификатору iPAS
		boolean registrationIPasAvailable = config.getRegistrationMode() != RegistrationMode.HARD;

		try
		{
			return CSABackRequestHelper.sendStartMobileRegistrationRq(login, appType, ConfirmStrategyType.sms, registrationIPasAvailable, devId, card, mobilePlatformService.findByPlatformId(appType).getPlatformName());
		}
		catch (MobileBankRegistrationNotFoundException e)
		{
			String message = "Регистрация приложения не может быть завершена, так как у Вас не подключена услуга «Мобильный банк» для рассылки SMS-паролей. Пожалуйста, подключите «Мобильный банк» в банкомате или отделении Сбербанка.";
			throw new FrontLogicException(message, e);
		}
		catch (FailureIdentificationException e)
		{
			throw new FailureIdentificationFrontException(e);
		}
		catch (SendSmsMessageException e)
		{
			String message = "Регистрация мобильного приложения не может быть завершена по соображениям безопасности. Для завершения регистрации, пожалуйста, обратитесь в Контактный Центр Сбербанка по телефону 8 800 555-55-50.";
			throw new FrontLogicException(message, e);
		}
		catch (CheckIMSIException e)
		{
			throw new SendSmsMessageFrontException(e.getPhones());
		}
		catch (BlockingRuleActiveException e)
		{
			throw new BlockingRuleException(e.getMessage(), e.getDate(), e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
		catch (LoginNotFoundException e)
		{
			throw new FailureIdentificationFrontException(e);
		}
		catch (BackLogicException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			throw new FrontException(e);
		}
	}

	protected void processResponse(Document response) throws FrontLogicException, FrontException
	{
		AuthenticationHelper.fillStartMobileRegistration(container, response);
		container.addParameter(Constants.VERSION_FIELD, version);

		if (StringHelper.isEmpty(container.getParameter(RequestConstants.OUID_TAG)))
		{
			throw new ResetMobileGUIDException("Приложение не зарегистрировано.");
		}
	}
}
