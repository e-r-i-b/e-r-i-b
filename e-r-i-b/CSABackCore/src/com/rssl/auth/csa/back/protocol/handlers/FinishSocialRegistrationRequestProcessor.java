package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * @author ыукпгтшт
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на завершение регистрации социального приложения finishSocialRegistrationRq
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
 * Параметры запроса:
 * OUID		            Идентификатор операции.     [1]
 * Password		        Пароль пользователя	        [1]
 * deviceState		    состояние устройства        [0..1]
 * devID                идентификатор устройства    [0..1]
 *
 * Параметры ответа:
 * GUID		            Идентфикатор созданного коннектров.  [1]
 * newPassword		    Признак того, что задан новый пароль.  [1]
 *
 * */

public class FinishSocialRegistrationRequestProcessor extends FinishMobileRegistrationRequestProcessor
{
	public static final String REQUEST_TYPE = "finishSocialRegistrationRq";
	public static final String RESPONCE_TYPE = "finishSocialRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo finishRegistration(final IdentificationContext identificationContext, String ouid, final String password, String deviceState, String deviceId, String appName) throws Exception
	{
		final SocialRegistrationOperation registrationOperation = identificationContext.findOperation(SocialRegistrationOperation.class, ouid, SocialRegistrationOperation.getLifeTime());
		try
		{
			trace("заявка " + registrationOperation.getOuid() + " успешно получена. Исполняем ее.");
			Connector connector = registrationOperation.execute(password, deviceState, deviceId, appName);
			info("заявка " + registrationOperation.getOuid() + " успешно исполнена.");
			LogThreadContext.setMGUID(connector.getGuid());
			ResponseInfo responceInfo = getSuccessResponseBuilder()
					.addParameter(Constants.GUID_TAG, connector.getGuid())
					.addParameter(Constants.IS_NEW_PASSWORD_TAG, registrationOperation.isPasswordChanged())
					.end().getResponceInfo();
			return new LogableResponseInfo(responceInfo);
		}
		catch (TooManyMobileConnectorsException e)
		{
			error("ошибка инициализации операции ", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildTooManyMobileConnectorsResponse(), e);
		}
	}
}