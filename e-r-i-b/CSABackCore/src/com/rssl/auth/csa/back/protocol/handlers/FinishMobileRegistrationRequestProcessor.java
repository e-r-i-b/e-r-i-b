package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.LogThreadContext;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на завершение регистрации мобильного приложения finishMobileRegistrationRq
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

public class FinishMobileRegistrationRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "finishMobileRegistrationRq";
	public static final String RESPONCE_TYPE = "finishMobileRegistrationRs";

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
		trace("проверяем входные данные");
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		String deviceState = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_STATE_TAG);
		String deviceId = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_ID_TAG);
		String appName = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.APP_NAME_TAG);
		return finishRegistration(context, ouid, password, deviceState, deviceId, appName);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	protected LogableResponseInfo finishRegistration(final IdentificationContext identificationContext, String ouid, final String password, String deviceState, String deviceId, String appName) throws Exception
	{
		final MobileRegistrationOperation registrationOperation = identificationContext.findOperation(MobileRegistrationOperation.class, ouid, MobileRegistrationOperation.getLifeTime());
		try
		{
			trace("заявка " + registrationOperation.getOuid() + " успешно получена. Исполняем ее.");
			Connector connector = registrationOperation.execute(password, deviceState, deviceId, appName);
			info("заявка " + registrationOperation.getOuid() + " успешно исполнена.");
			LogThreadContext.setMGUID(connector.getGuid());
			ResponseInfo responceInfo = getSuccessResponseBuilder()
					.addParameter(Constants.GUID_TAG, connector.getGuid())
					.addParameter(Constants.IS_NEW_PASSWORD_TAG, registrationOperation.isPasswordChanged())
					.addParameter(Constants.PROFILE_ID_TAG, identificationContext.getProfile().getId())
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