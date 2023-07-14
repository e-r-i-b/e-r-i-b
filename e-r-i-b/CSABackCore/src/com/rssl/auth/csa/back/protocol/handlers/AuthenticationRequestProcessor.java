package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.AuthenticationOperation;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.exceptions.IPasUnavailableException;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.exceptions.RetryIPasUnavailableException;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *
 * Пармерты запроса:
 * login		        логин пользователя. 	[1]
 * password		        пароль пользователя	    [1]
 *
 * Параметры ответа:
 * blockingTimeout              время оставшееся до снятия                                  [0..1]
 *                              блокировки в сек в случае кода ответа
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * authToken		            токен аутентификации.                                       [0..1]
 * UserInfo		                Информация о пользователе                                   [0..1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      preferredConfirmType    предпочтительный способ подтверждения                       [1]
 * ConnectorInfo                Информация о коннекторе                                     [0..n]
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
 */

public class AuthenticationRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "authenticationRq";
	public static final String RESPONCE_TYPE = "authenticationRs";

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
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		return authenticate(login, password, context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return LogIdentificationContext.createByLogin(login, ConfigFactory.getConfig(Config.class).isPostAuthenticationSyncAllowed());
	}

	private LogableResponseInfo authenticate(String login, String password, final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			trace("создаем заявку на аутентификацию для профиля " + identificationContext.getProfile().getId());
			AuthenticationOperation operation = new AuthenticationOperation(identificationContext);
			operation.initialize(login);
			trace("проводим аутентификацию");
			Connector connector = operation.execute(password);
			List<CSAConnector> connectors = CSAConnector.findNotClosedByProfileID(connector.getProfile().getId());
			return new LogableResponseInfo(getSuccessResponseBuilder()
					.addAuthToken(operation)
					.addUserInfo(connector.getProfile())
					.addConnectorsInfo(connectors)
					.end().getResponceInfo());
		}
		catch (RetryIPasUnavailableException e)
		{
			error("ошибка аутентификации", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildRetryIpasUnavailableResponse(e.getDescription()), e);
		}
		catch (IPasUnavailableException e)
		{
			//Нас интересует только недоступность iPas и только в этом случае. ТЗ про остальные случаи молчит.
			error("ошибка аутентификации", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildIpasUnavailableResponse(e.getConnector()), e);
		}
		catch (ConnectorNotFoundException e)
		{
			error("ошибка аутентификации", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(), e);
		}
	}
}
