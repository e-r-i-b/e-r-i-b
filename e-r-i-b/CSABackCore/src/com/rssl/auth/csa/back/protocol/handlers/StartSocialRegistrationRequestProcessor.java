package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.exceptions.TooManyRequestException;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.LoginType;

/**
 * @author sergunin
 * @ created 03.09.14
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
 * devID                идентификатор пользователя    [0..1]
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
public class StartSocialRegistrationRequestProcessor extends StartMobileRegistrationRequestProcessor
{
	public static final String REQUEST_TYPE = "startSocialRegistrationRq";
	public static final String RESPONCE_TYPE = "startSocialRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo startRegistration(final IdentificationContext identificationContext, String login, String deviceInfo, String confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws Exception
	{
		try
		{
			Connector connector = Operation.findAuthenticableConnecorByLogin(login);
			if (!registrationIPasAvailable && connector.getType() == ConnectorType.TERMINAL)
			{
				throw new RestrictionException("Для регистрации социальных приложений необходимо использовать логин и пароль, заданные при регистрации в Сбербанк Онлайн");
			}

			trace("создаем заявку на регистрацию социального приложения для профиля " + identificationContext.getProfile().getId());
			LoginType loginType = connector.getType() == ConnectorType.TERMINAL ? LoginType.TERMINAL : LoginType.CSA;
			SocialRegistrationOperation operation = createSocialRegistrationOperation(identificationContext, deviceInfo, confirmStrategyType, deviceId, loginType, appName);

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

	private ResponseInfo buildSuccessResponse(SocialRegistrationOperation operation) throws Exception
	{
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addUserInfo(operation.getProfile())
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	private SocialRegistrationOperation createSocialRegistrationOperation(IdentificationContext identificationContext, String deviceInfo, String confirmStrategyType, String deviceId, LoginType registrationLoginType, String appName) throws Exception
	{
        SocialRegistrationOperation operation = new SocialRegistrationOperation(identificationContext);
		operation.initialize(deviceInfo, ConfirmStrategyType.valueOf(confirmStrategyType), deviceId, appName, registrationLoginType);
		return operation;
	}
}