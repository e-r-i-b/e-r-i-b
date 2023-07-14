package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.RestorePasswordOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author krenev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на начало восстановления пароля startRestorePasswordRq.
 *
 * Упрощенный процесс восстановления выглядит сл образом:
 * 1) клиент начинает восстановление пароля, введя свой логин.
 * 2) идет запрос startRestorePasswordRq в ЦСА BACK
 * 3) происходит идентификация пользователя, отсылка смс кода на подтверждение операции.
 *    ЦСА Back возвращает вместе с параметрами подтверждения информацию о пользователе.
 * 4) пользователь вводит код подтверждения
 * 5) идет запрос confirmOperationRq на подтверждение операции
 * 6) после успешного подтверждения пользователю в зависимости от типа логина предлагается завершить восстановление вводом пароля
 * 7) идет запрос finishRestorePasswordRq c передачей нового пароля или без его передачи, если требуется автоматическая генерация пароля.
 * 8) BACK по регистрирует пользователя(исполняет операцию восстановления пароля).
 *
 * Параметры запроса:
 * login		        логин пользователя. 	[1]
 *
 * Параметры ответа:
 * OUID		                    Идентфикатор операции.                                      [0..1]
 * UserInfo		                Информация о пользователе                                   [0..1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 * ConnectorInfo                Информация о коннекторе                                     [0..1]
 *      GUID                    идентификатор коннектора                                    [1]
 *      deviceState             предыдущее состояние устройства                             [0..1]
 *      deviceInfo              информация об устройстве                                    [0..1]
 *      cbCode                  Подразделение пользователя                                  [1]
 *      userId                  Цифровой логин пользователя                                 [1]
 *      cardNumber              "карта входа"                                               [1]
 *      login                   логин/алиас                                                 [0..1]
 *      type                    тип коннектора(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            дата создания коннектора                                    [1]
 *      passwordCreationDate    дата создания пароля                                        [0..1]
 * ConfirmParameters            Параметры подтверждения	                                    [0..1]
 *      Timeout		            Таймаут ожидания подтверждения	                            [1]
 *      Attempts		        Количество оставшихся попыток ввода кода подтверждения. 	[1]
 */
public class StartRestorePasswordRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "startRestorePasswordRq";
	public static final String RESPONCE_TYPE = "startRestorePasswordRs";

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
		String confirmStrategyType = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_PARAM_NAME);
		return startRestorePassword(login, confirmStrategyType, context, body);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return LogIdentificationContext.createByLogin(login, false);
	}

	private LogableResponseInfo startRestorePassword(String login, String confirmStrategyType, final IdentificationContext identificationContext, Document body) throws Exception
	{
		try
		{
			trace("создаем заявку на восстановление пароля для профиля "+identificationContext.getProfile().getId());
			RestorePasswordOperation restorePasswordOperation = createRestorePasswordOperation(identificationContext, login, confirmStrategyType, body);
			trace("строим и отправляем ответ об успешной обработке запроса");
			return new LogableResponseInfo(buildSuccessResponce(restorePasswordOperation));
		}
		catch (ConnectorNotFoundException e)
		{
			trace("невозможно восстановить пароль", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(),e);
		}
	}

	/**
	 * Сохранить данные об устройстве клиента в операцию
	 * @param operation - операция
	 * @param body - документ десериализованный из запроса CSAFront (должен содержать информацию об устройстве)
	 */
	private void setRSADataToOperation(RestorePasswordOperation operation, Document body)
	{
		operation.setRSAData(readRSAData(body));
	}

	/**
	 *
	 * @param body
	 * @return
	 */
	private Map<String, String> readRSAData(Document body)
	{
		Map<String, String> rsaData = new TreeMap<String, String>();

		rsaData.put(DEVICE_TOKEN_FSO_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), DEVICE_TOKEN_FSO_PARAMETER_NAME));
		rsaData.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), DEVICE_TOKEN_COOKIE_PARAMETER_NAME));
		rsaData.put(DEVICE_PRINT_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), DEVICE_PRINT_PARAMETER_NAME));
		rsaData.put(DOM_ELEMENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), DOM_ELEMENTS_PARAMETER_NAME));
		rsaData.put(JS_EVENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), JS_EVENTS_PARAMETER_NAME));

		rsaData.put(PAGE_ID_PARAMETER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), PAGE_ID_PARAMETER_NAME));
		rsaData.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_ENCODING_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_ACCEPT_ENCODING_HEADER_NAME));
		rsaData.put(HTTP_USER_AGENT_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_USER_AGENT_HEADER_NAME));
		rsaData.put(HTTP_REFERRER_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_REFERRER_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_CHARS_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_ACCEPT_CHARS_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_HEADER_NAME, XmlHelper.getSimpleElementValue(body.getDocumentElement(), HTTP_ACCEPT_HEADER_NAME));

		return rsaData;
	}

	private ResponseInfo buildSuccessResponce(RestorePasswordOperation operation) throws Exception
	{
		Connector connector = operation.getConnector();
		return getSuccessResponseBuilder()
				.addOUID(operation)
				.addUserInfo(connector.getProfile())
				.addConnectorInfo(connector)
				.addConfirmParameters(operation)
				.end().getResponceInfo();
	}

	private RestorePasswordOperation createRestorePasswordOperation(IdentificationContext identificationContext, String login, String confirmStrategyType, Document body) throws Exception
	{
		RestorePasswordOperation operation = new RestorePasswordOperation(identificationContext);
		setRSADataToOperation(operation, body);
		operation.initialize(login, ConfirmStrategyType.valueOf(confirmStrategyType));
		return operation;
	}
}
