package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ActualizationLogonInfoOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ������������ ���������� � ����� �������(�������� ������ �����������).
 * �������� �����:
 * 1) ������������ ������ �����/������
 * 2) ���� ������ startCreateSessionRq � ��� BACK
 * 3) � ����� �������� ������������ ������, ���� ������������� �������� �����
 * 4) ����� ������������ ������� � ������� �� � ��������� ������������� �������� �����.
 * 5) �� �������� ������ finishCreateSessionRq � ��� BACK
 * 6) ��� BACK ���������� ���������� �� ��������������������� ������������.
 * ���� ����� �������������� ��������������, ��� � ������� ���������������� ��������� CSA-�����������, �� ������������ ����� ������
 * ��������� ������������ ���������� � �����. ��� ���� ����� ������ ���������� ������������ ������ ������� ������� � ���� ����� 1 �� ���.
 * ����� ������ ������ ������������ ����� �������� ������ �� ������������ ���������� � ����� � ��������� ���������� ������������� ������.
 * � ���� ������������ ����������� ��������(������������ ����� ��������������, ������� ��������� �������� � ������� �� � ��.).
 *
 * �������� �������:
 * OUID		                    ������������� �������� ������������.                        [0..1]
 * GUID		                    ������������� ����������, ������� ��������� ��������        [1]
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
 * */

public class ActualizationLogonInfoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "actualizeLogonInfoRq";
	public static final String RESPONCE_TYPE = "actualizeLogonInfoRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String guid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.GUID_TAG);

		return actualizeLogonInfo(ouid, guid);
	}

	private ResponseInfo actualizeLogonInfo(final String ouid, final String guid) throws Exception
	{
		final IdentificationContext identificationContext = IdentificationContext.createByOperationUID(ouid);
		final ActualizationLogonInfoOperation operation = identificationContext.findOperation(ActualizationLogonInfoOperation.class, ouid, UserLogonOperation.getLifeTime());
		trace("������ " + operation.getOuid() + " ������� ��������. ��������� ��.");
		Connector connector = operation.execute(guid);
		trace("������ " + operation.getOuid() + " ������� ���������. ������������ ����.");
		return prepareLogon(identificationContext, connector);
	}
}