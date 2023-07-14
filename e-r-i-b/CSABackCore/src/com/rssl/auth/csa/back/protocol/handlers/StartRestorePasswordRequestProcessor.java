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
 * ���������� ������� �� ������ �������������� ������ startRestorePasswordRq.
 *
 * ���������� ������� �������������� �������� �� �������:
 * 1) ������ �������� �������������� ������, ����� ���� �����.
 * 2) ���� ������ startRestorePasswordRq � ��� BACK
 * 3) ���������� ������������� ������������, ������� ��� ���� �� ������������� ��������.
 *    ��� Back ���������� ������ � ����������� ������������� ���������� � ������������.
 * 4) ������������ ������ ��� �������������
 * 5) ���� ������ confirmOperationRq �� ������������� ��������
 * 6) ����� ��������� ������������� ������������ � ����������� �� ���� ������ ������������ ��������� �������������� ������ ������
 * 7) ���� ������ finishRestorePasswordRq c ��������� ������ ������ ��� ��� ��� ��������, ���� ��������� �������������� ��������� ������.
 * 8) BACK �� ������������ ������������(��������� �������� �������������� ������).
 *
 * ��������� �������:
 * login		        ����� ������������. 	[1]
 *
 * ��������� ������:
 * OUID		                    ������������ ��������.                                      [0..1]
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
 * ConfirmParameters            ��������� �������������	                                    [0..1]
 *      Timeout		            ������� �������� �������������	                            [1]
 *      Attempts		        ���������� ���������� ������� ����� ���� �������������. 	[1]
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
			trace("������� ������ �� �������������� ������ ��� ������� "+identificationContext.getProfile().getId());
			RestorePasswordOperation restorePasswordOperation = createRestorePasswordOperation(identificationContext, login, confirmStrategyType, body);
			trace("������ � ���������� ����� �� �������� ��������� �������");
			return new LogableResponseInfo(buildSuccessResponce(restorePasswordOperation));
		}
		catch (ConnectorNotFoundException e)
		{
			trace("���������� ������������ ������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildLoginNotFoundResponse(),e);
		}
	}

	/**
	 * ��������� ������ �� ���������� ������� � ��������
	 * @param operation - ��������
	 * @param body - �������� ����������������� �� ������� CSAFront (������ ��������� ���������� �� ����������)
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
