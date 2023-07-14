package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.exceptions.IPasUnavailableException;
import com.rssl.auth.csa.back.exceptions.RetryIPasUnavailableException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.operations.AuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.AutoUserRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на начало входа пользователя.
 * Сценарий входа:
 * 1) пользователь вводит логин/пароль
 * 2) идет запрос startCreateSessionRq в ЦСА BACK
 * 3) В отете приходит идентфикатор ошибка, либо идентификатор операции входа
 * 4) Фронт осуществляет форвард в целевую АС с передачей идентифкатора операции входа.
 * 5) АС вызывает запрос finishCreateSessionRq в ЦСА BACK
 * 6) ЦСА BACK возвращает информацию ою аутентифицировавшемся пользователе.
 *
 * Пармерты запроса:
 * login		        логин пользователя. 	[1]
 * password		        пароль пользователя	    [1]
 *
 * Параметры ответа:
 * blockingTimeout              время оставшееся до снятия                                  [0..1]
 *                              блокировки в сек в случае кода ответа
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    идентификатор операции входа.                               [0..1]
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
 * ConfirmParameters            Параметры подтверждения	                                    [0] в текущей версии не передаются.
 *      Timeout		            Таймаут ожидания подтверждения	                            [1]
 *      Attempts		        Количество оставшихся попыток ввода кода подтверждения. 	[1]
 *
 *
 *
 * Если после аутентификации обнаруживается, что у клиента зарегистрировано несколько CSA-коннекторов, то возвращается ответ ошибка
 * Требуется актуализация информации о входе. При этом фронт должен отобразить пользвоателю список логинов клиента и дать выбор 1 из них.
 * После выбора логина пользователя фронт посылает запрос на актуализацию информации о входе с передачей выбранного пользователем логина.
 * И вход продолжается стандарнтым способом(возвращается токен аутентификации, который требуется передать в ЦЕЛЕВУЮ АС и тд.).
 * Формат ответа в случае обнаружения нескольких логинов:
 * OUID		                    идентификатор операции актуализации информации о входе.     [0..1]
 * ConnectorInfo                Информация о коннекторе                                     [1..n]
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
 *
 * В случае недоступности смежных сервисов (пока только iPas) вместе с ошибкой возвращается информация о контексте идентифкации пользователя.
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
 * */

public class StartCreateSessionRequestProcessor extends StartCreateSessionRequestProcessorBase
{
	public static final String REQUEST_TYPE = "startCreateSessionRq";
	public static final String RESPONCE_TYPE = "startCreateSessionRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("проверяем входные данные");
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		TreeMap<String, String> rsaData = createRSAData(requestInfo);
		return createSession(login, password, context, rsaData);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		return LogIdentificationContext.createByLogin(login, ConfigFactory.getConfig(Config.class).isPostAuthenticationSyncAllowed());
	}

	private LogableResponseInfo createSession(final String login, final String password, IdentificationContext identificationContext, Map<String, String> parameters) throws Exception
	{
		Config config = ConfigFactory.getConfig(Config.class);
		try
		{
			boolean postAuthenticationSyncAllowed = config.isPostAuthenticationSyncAllowed();
			trace("создаем заявку на аутентификацию для профиля " + identificationContext.getProfile().getId());
			AuthenticationOperation authenticationOperation = createAuthenticationOperation(identificationContext, login);
			trace("проводим аутентификацию");
			Connector connector = authenticationOperation.execute(password);
			if (postAuthenticationSyncAllowed && connector.getType()== ConnectorType.TERMINAL)
			{
				//Идентифицирующие клиента данные могут изменениться после аутентификации
				identificationContext = IdentificationContext.createByLogin(login, postAuthenticationSyncAllowed);
			}

			// если зашел алиасу старой цса
			if(config.isAccessAutoRegistration() && (connector.getType() == ConnectorType.TERMINAL && !Utils.isIPasLogin(login)))
			{
				connector = autoRegistrationConnector(identificationContext, connector, password);
			}

			return new LogableResponseInfo(prepareLogon(identificationContext, connector, parameters));
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
	private AuthenticationOperation createAuthenticationOperation(IdentificationContext identificationContext, String login) throws Exception
	{
		AuthenticationOperation operation = new AuthenticationOperation(identificationContext);
		operation.initialize(login);
		return operation;
	}

	private Connector autoRegistrationConnector(IdentificationContext identificationContext, Connector connector, String password)
	{
		try
		{
			info("Проводим авторегистрацию, если клиент зашел по алиасу старой цса");
			AutoUserRegistrationOperation autoUserRegOperation = new AutoUserRegistrationOperation(identificationContext);
			autoUserRegOperation.initialize(connector);

			return autoUserRegOperation.execute(password);
		}
		catch (Exception ignore)
		{
			// не получилось зарегистрировать новый, возвращаем старый
			return connector;
		}
	}

	@Override
	protected EventsType getEventType()
	{
		return EventsType.FAILED_LOGIN_ATTEMPT;
	}

	protected TreeMap<String, String> createRSAData(RequestInfo info)
	{
		TreeMap<String, String> map = new TreeMap<String, String>();

		Node rsaSource = info.getBody().getDocumentElement().getElementsByTagName(RSA_DATA_NAME).item(0);
		map.put(DOM_ELEMENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DOM_ELEMENTS_PARAMETER_NAME));
		map.put(JS_EVENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, JS_EVENTS_PARAMETER_NAME));
		map.put(DEVICE_PRINT_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_PRINT_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_FSO_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_FSO_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		Node headerSource = info.getBody().getDocumentElement().getElementsByTagName(HEADER_DATA_NAME).item(0);
		map.put(HTTP_ACCEPT_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_HEADER_NAME));
		map.put(HTTP_ACCEPT_CHARS_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_CHARS_HEADER_NAME));
		map.put(HTTP_ACCEPT_ENCODING_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_ENCODING_HEADER_NAME));
		map.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		map.put(HTTP_REFERRER_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_REFERRER_HEADER_NAME));
		map.put(HTTP_USER_AGENT_HEADER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_USER_AGENT_HEADER_NAME));
		map.put(PAGE_ID_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) headerSource, PAGE_ID_PARAMETER_NAME));

		return map;
	}
}
