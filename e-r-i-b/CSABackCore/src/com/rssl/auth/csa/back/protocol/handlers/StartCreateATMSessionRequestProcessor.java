package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.CardNotActiveAndMainException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ATMAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ������ ����� ������������ ����� ���
 *
 * �������� �������:
 * cardNumber                   ����� �����                                                 [1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    ������������� �������� �����.                               [0..1]
 * nodeInfo                     ���������� � �����                                          [1]
 *      host                    ���� �����                                                  [1]
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
 */
public class StartCreateATMSessionRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE    = "startCreateATMSessionRq";
	private static final String RESPONSE_TYPE   = "startCreateATMSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		return doRequest(context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String cardNumber = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		return LogIdentificationContext.createByCardNumber(cardNumber);
	}

	private LogableResponseInfo doRequest(final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			trace("������� ������ �� �������������� ��� ������� " + identificationContext.getProfile().getId());
			ATMAuthenticationOperation operation = createAtmAuthenticationOperation(identificationContext);
			trace("�������� ��������������");
			Connector connector = operation.execute();

			return new LogableResponseInfo(prepareApiLogon(identificationContext, connector));
		}
		catch (CardNotActiveAndMainException e)
		{
			String errorMessage = "������ �������������� - ����� ������ ���� �������� � ��������";
			error(errorMessage, e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildAuthenticationFailedResponse(errorMessage), e);
		}
	}

	private ATMAuthenticationOperation createAtmAuthenticationOperation(IdentificationContext context) throws Exception
	{
		ATMAuthenticationOperation operation = new ATMAuthenticationOperation(context);
		operation.initialize();
		return operation;
	}
}
