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
 * ���������� ������� �� ������ ����� ������������.
 * �������� �����:
 * 1) ������������ ������ �����/������
 * 2) ���� ������ startCreateSessionRq � ��� BACK
 * 3) � ����� �������� ������������ ������, ���� ������������� �������� �����
 * 4) ����� ������������ ������� � ������� �� � ��������� ������������� �������� �����.
 * 5) �� �������� ������ finishCreateSessionRq � ��� BACK
 * 6) ��� BACK ���������� ���������� �� ��������������������� ������������.
 *
 * �������� �������:
 * login		        ����� ������������. 	[1]
 * password		        ������ ������������	    [1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    ������������� �������� �����.                               [0..1]
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 * ConnectorInfo                ���������� � ����������                                     [0..1]
 *      GUID                    ������������� ����������                                    [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
 * ConfirmParameters            ��������� �������������	                                    [0] � ������� ������ �� ����������.
 *      Timeout		            ������� �������� �������������	                            [1]
 *      Attempts		        ���������� ���������� ������� ����� ���� �������������. 	[1]
 *
 *
 *
 * ���� ����� �������������� ��������������, ��� � ������� ���������������� ��������� CSA-�����������, �� ������������ ����� ������
 * ��������� ������������ ���������� � �����. ��� ���� ����� ������ ���������� ������������ ������ ������� ������� � ���� ����� 1 �� ���.
 * ����� ������ ������ ������������ ����� �������� ������ �� ������������ ���������� � ����� � ��������� ���������� ������������� ������.
 * � ���� ������������ ����������� ��������(������������ ����� ��������������, ������� ��������� �������� � ������� �� � ��.).
 * ������ ������ � ������ ����������� ���������� �������:
 * OUID		                    ������������� �������� ������������ ���������� � �����.     [0..1]
 * ConnectorInfo                ���������� � ����������                                     [1..n]
 *      GUID                    ������������� ����������                                    [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
 *
 * � ������ ������������� ������� �������� (���� ������ iPas) ������ � ������� ������������ ���������� � ��������� ������������ ������������.
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 * ConnectorInfo                ���������� � ����������                                     [0..1]
 *      GUID                    ������������� ����������                                    [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
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
		trace("��������� ������� ������");
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
			trace("������� ������ �� �������������� ��� ������� " + identificationContext.getProfile().getId());
			AuthenticationOperation authenticationOperation = createAuthenticationOperation(identificationContext, login);
			trace("�������� ��������������");
			Connector connector = authenticationOperation.execute(password);
			if (postAuthenticationSyncAllowed && connector.getType()== ConnectorType.TERMINAL)
			{
				//���������������� ������� ������ ����� ������������ ����� ��������������
				identificationContext = IdentificationContext.createByLogin(login, postAuthenticationSyncAllowed);
			}

			// ���� ����� ������ ������ ���
			if(config.isAccessAutoRegistration() && (connector.getType() == ConnectorType.TERMINAL && !Utils.isIPasLogin(login)))
			{
				connector = autoRegistrationConnector(identificationContext, connector, password);
			}

			return new LogableResponseInfo(prepareLogon(identificationContext, connector, parameters));
		}
		catch (RetryIPasUnavailableException e)
		{
			error("������ ��������������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildRetryIpasUnavailableResponse(e.getDescription()), e);
		}
		catch (IPasUnavailableException e)
		{
			//��� ���������� ������ ������������� iPas � ������ � ���� ������. �� ��� ��������� ������ ������.
			error("������ ��������������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildIpasUnavailableResponse(e.getConnector()), e);
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ ��������������", e);
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
			info("�������� ���������������, ���� ������ ����� �� ������ ������ ���");
			AutoUserRegistrationOperation autoUserRegOperation = new AutoUserRegistrationOperation(identificationContext);
			autoUserRegOperation.initialize(connector);

			return autoUserRegOperation.execute(password);
		}
		catch (Exception ignore)
		{
			// �� ���������� ���������������� �����, ���������� ������
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
