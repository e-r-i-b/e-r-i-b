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
 * �������� �������:
 * login		        ����� ������������. 	[1]
 * password		        ������ ������������	    [1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * authToken		            ����� ��������������.                                       [0..1]
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      preferredConfirmType    ���������������� ������ �������������                       [1]
 * ConnectorInfo                ���������� � ����������                                     [0..n]
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
		trace("��������� ������� ������");
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
			trace("������� ������ �� �������������� ��� ������� " + identificationContext.getProfile().getId());
			AuthenticationOperation operation = new AuthenticationOperation(identificationContext);
			operation.initialize(login);
			trace("�������� ��������������");
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
}
