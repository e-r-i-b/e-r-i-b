package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.EnrollInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;
import static com.rssl.phizic.context.Constants.PAGE_ID_PARAMETER_NAME;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на завершение регистрации пользователя finishUserRegistrationRq
 *
 * Упрощенный процесс регистрации выгляит сл образом:
 * 1) клиент начинает регистрацию, введя номер своей карты.
 * 2) идет запрос startUserRegistrationRq в ЦСА BACK
 * 3) происходит идентификация пользователя, проверка возможности регистрации и отсылка смс кода на подтверждение операции
 * 4) пользователь вводит код подтверждения
 * 5) идет запрос confirmOperationRq на подтверждение операции
 * 6) после успешного подтверждения пользователю предлагается завершить регистрацию вводом логина и пароля
 * 7) идет запрос finishUserRegistrationRq c передачей логина и пароля
 * 8) BACK по регистрирует пользователя(исполняет операцию регистрации).
 *
 * Параметры запроса:
 * OUID		            Идентификатор операции.     [1]
 * Login		        Логин пользователя. 	    [1]
 * Password		        Пароль пользователя	        [1]
 * NeedNotification     Необходимость оповещения.   [0-1]
 *
 * Параметры ответа:
 */

public class FinishUserRegistrationRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE = "finishUserRegistrationRq";
	private static final String RESPONCE_TYPE = "finishUserRegistrationRs";

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
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String needNotification = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.NEED_NOTIFICATION_TAG);

		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		//finishUserRegistration
		final UserRegistrationOperation registrationOperation = findOperation(ouid, context);
		trace("заявка " + registrationOperation.getOuid() + " успешно получена. Проверка во ФМ.");
		doFraudControl(registrationOperation, requestInfo);
		trace("заявка " + registrationOperation.getOuid() + " успешно получена. Исполняем ее.");
		Connector connector = registrationOperation.execute(login, password, StringHelper.isEmpty(needNotification) || Boolean.parseBoolean(needNotification));
		info("заявка " + registrationOperation.getOuid() + " успешно исполнена.");

		// если сразу после регистрации требуется вход
		Config config = ConfigFactory.getConfig(Config.class);
		if(config.isPostRegistrationLogin())
		{
			info("Входим по созданному коннектору " + connector.getGuid());
			return new LogableResponseInfo(prepareLogon(context, connector));
		}

		return new LogableResponseInfo(buildSuccessResponse());
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	protected UserRegistrationOperation findOperation(String ouid, final IdentificationContext identificationContext) throws Exception
	{
		return identificationContext.findOperation(UserRegistrationOperation.class, ouid, UserRegistrationOperation.getLifeTime());
	}

	protected void doFraudControl(UserRegistrationOperation operation, RequestInfo requestInfo) throws ProhibitionOperationFraudGateException
	{
		try
		{
			initialize(requestInfo);

			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.ENROLL);
			//noinspection unchecked
			sender.initialize(new EnrollInitializationData(operation.getUserId(), operation.getProfile().getTb(), requestInfo.getIP(), operation.getProfileId()));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw e;
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			RSAContext.clear();
			HeaderContext.clear();
		}
	}

	private void initialize(RequestInfo requestInfo)
	{
		Map<String, String> data = getContextData(requestInfo);
		RSAContext.initialize(data);
		HeaderContext.initialize(data);
	}

	private Map<String, String> getContextData(RequestInfo requestInfo)
	{
		Map<String, String> map = new TreeMap<String, String>();

		Node rsaSource = requestInfo.getBody().getDocumentElement().getElementsByTagName(RSA_DATA_NAME).item(0);
		map.put(DOM_ELEMENTS_PARAMETER_NAME,        XmlHelper.getSimpleElementValue((Element) rsaSource, DOM_ELEMENTS_PARAMETER_NAME));
		map.put(JS_EVENTS_PARAMETER_NAME,           XmlHelper.getSimpleElementValue((Element) rsaSource, JS_EVENTS_PARAMETER_NAME));
		map.put(DEVICE_PRINT_PARAMETER_NAME,        XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_PRINT_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_FSO_PARAMETER_NAME,    XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_FSO_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		Node headerSource = requestInfo.getBody().getDocumentElement().getElementsByTagName(HEADER_DATA_NAME).item(0);
		map.put(HTTP_ACCEPT_HEADER_NAME,            XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_HEADER_NAME));
		map.put(HTTP_ACCEPT_CHARS_HEADER_NAME,      XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_CHARS_HEADER_NAME));
		map.put(HTTP_ACCEPT_ENCODING_HEADER_NAME,   XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_ENCODING_HEADER_NAME));
		map.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME,   XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		map.put(HTTP_REFERRER_HEADER_NAME,          XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_REFERRER_HEADER_NAME));
		map.put(HTTP_USER_AGENT_HEADER_NAME,        XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_USER_AGENT_HEADER_NAME));
		map.put(PAGE_ID_PARAMETER_NAME,             XmlHelper.getSimpleElementValue((Element) headerSource, PAGE_ID_PARAMETER_NAME));

		return map;
	}
}
