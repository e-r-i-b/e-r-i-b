package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.*;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.verification.VerifyUserRegistrationModeOperation;
import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на начало регистрации мобильного приложения startMobileRegistrationRq.
 *
 * Упрощенный процесс регистрации мобильного приложения выглядит сл образом:
 * 1) клиент начинает регистрацию мобильного приложения, введя свой логин.
 * 2) идет запрос startMobileRegistrationRq в ЦСА BACK с передачей логина и информации о мобильном устройстве
 * 3) происходит идентификация пользователя, отсылка смс кода на подтверждение операции.
 *    ЦСА Back возвращает вместе с параметрами подтверждения информацию о пользователе.
 * 4) пользователь вводит код подтверждения и пароль на доступ к приложенияю
 * 5) идет запрос confirmOperationRq на подтверждение операции
 * 6) после успешного подтверждения, идет запрос окончания регистрации мобильного приложения finishMobileRegistrationRq с передачей пароля на вход в приложение.
 * 7) ЦСА Back производит регистрацию нового коннектора для мобильного устройства и возвращает mGUID. Успешное окончание регистрации не приводит к автоматической аутентификации клиента в ЦСА и созданию сессии.
 * 8) Для открытия сессии в ЦСА требуется послать запрос на ее открытие CreateMobileSessionRq c передачей mGUID. В ответ на запрос придет идентификатор сессии и информация о пользователе.   
 *
 *
 * Параметры запроса:
 * login		        логин пользователя. 	    [1]
 * deviceInfo           информация об устройстве    [1]
 * registrationIPasAvailable    доступность регистрации по логину iPas              [1]
 * devID                идентификатор устройства    [0..1]
 *
 * Параметры ответа:
 * OUID		            Идентфикатор операции.                                      [0..1]
 * UserInfo		        Информация о пользователе                                   [0..1]
 *      firstname       Имя пользователя                                            [1]
 *      patrname        Отчество пользователя                                       [0..1]       
 *      surname         Фамилия пользователя                                        [1]
 *      birthdate       Дата рождения пользователя                                  [1]
 *      passport        ДУЛ пользователя                                            [1]
 * ConfirmParameters    Параметры подтверждения	                                    [0..1]
 *      Timeout		    Таймаут ожидания подтверждения	                            [1]
 *      Attempts		Количество оставшихся попыток ввода кода подтверждения. 	[1]
 */
public class StartMobileRegistrationRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "startMobileRegistrationRq";
	public static final String RESPONCE_TYPE = "startMobileRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String deviceInfo = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_INFO_TAG);
		String confirmStrategyType = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_PARAM_NAME);
		boolean registrationIPasAvailable = Boolean.valueOf(XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.REGISTRATION_IPAS_TAG));
		String deviceId = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_ID_TAG);
        String card = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_TAG);
        String appName = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.APP_NAME_TAG);
		return startRegistration(context, login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, card, appName);
	}

    protected void checkCardsLastFourNumbers(String card, List<String> userCards) throws Exception
    {
        if(card == null)
            return;
	    if (userCards == null)
	    {
		    return;
	    }

        Config.UserInfoProviderName userInfoProvider = ConfigFactory.getConfig(Config.class).getUserInfoProvider();

        if(userInfoProvider == Config.UserInfoProviderName.MOBILE_BANK)
            return;

        Pattern pattern = Pattern.compile("\\d*" + card + "$");
        for(String cNumber : userCards) {
            Matcher matcher = pattern.matcher(cNumber);
            if(matcher.matches())
                return;
        }

        trace("Карта *" + card + " не принадлежи пользователю");
        throw new IdentificationFailedException("Вы неправильно ввели логин, SMS-пароль или последние цифры номера карты. Пожалуйста, проверьте указанные данные.");
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String card = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_TAG);
		return card == null ? LogIdentificationContext.createByLogin(login, false):LogIdentificationContext.createByLoginDirect(login);
	}

    protected LogableResponseInfo startRegistration(final IdentificationContext identificationContext, String login, String deviceInfo, String confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws Exception
	{
		try
		{
			Connector connector = Operation.findAuthenticableConnecorByLogin(login);
			boolean regIPasAvailable = registrationIPasAvailable;

			//в «жестком» режиме самостоятельной регистрации запрещена регистрация мобильных приложений по идентификатору iPAS
			//проверяем что у клиента не включен индивидуальный жесткий режим самостоятельной регистрации
			VerifyUserRegistrationModeOperation verifyOperation = new VerifyUserRegistrationModeOperation();
			UserRegistrationMode userRegistrationMode = verifyOperation.getUserRegistrationMode(identificationContext.getProfile());

			if (userRegistrationMode != null && userRegistrationMode != UserRegistrationMode.DEFAULT)
				regIPasAvailable = userRegistrationMode != UserRegistrationMode.HARD;

			if (!regIPasAvailable && connector.getType() == ConnectorType.TERMINAL)
			{
				throw new RestrictionException("Для регистрации мобильных приложений необходимо использовать логин и пароль, заданные при регистрации в Сбербанк Онлайн");
			}

			trace("создаем заявку на регистрацию мобильного приложения для профиля " + identificationContext.getProfile().getId());
			LoginType loginType = connector.getType() == ConnectorType.TERMINAL ? LoginType.TERMINAL : LoginType.CSA;
			MobileRegistrationOperation operation = createMobileRegistrationOperation(identificationContext, deviceInfo, confirmStrategyType, deviceId, loginType, appName);

			checkCardsLastFourNumbers(card, operation.getUserCards());

			trace("строим и отправляем ответ об успешной обработке запроса");
			return new LogableResponseInfo(buildSuccessResponse(operation));
		}
		catch (TooManyRequestException e)
		{
			error("ошибка инициализации операции ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileRegistrationRequestResponse(), e);
		}
		catch (TooManyMobileConnectorsException e)
		{
			error("ошибка инициализации операции ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileConnectorsResponse(), e);
		}
		catch (ConnectorNotFoundException e)
		{
			error("ошибка аутентификации", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(), e);
		}
	}

	private ResponseInfo buildSuccessResponse(MobileRegistrationOperation operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addUserInfo(operation.getProfile())
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	private MobileRegistrationOperation createMobileRegistrationOperation(IdentificationContext identificationContext, String deviceInfo, String confirmStrategyType, String deviceId, LoginType registrationLoginType, String appName) throws Exception
	{
		MobileRegistrationOperation operation = new MobileRegistrationOperation(identificationContext);
		operation.initialize(deviceInfo, ConfirmStrategyType.valueOf(confirmStrategyType), deviceId, appName, registrationLoginType);
		return operation;
	}
}