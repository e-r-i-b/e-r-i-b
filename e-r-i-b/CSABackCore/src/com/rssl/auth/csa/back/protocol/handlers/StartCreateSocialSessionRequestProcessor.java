package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.handlers.data.StartCreateMobileSessionData;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.SocialAuthenticationOperation;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import com.rssl.phizic.utils.StringHelper;


/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ������ ����� ������������ ����� ����
 *
 * �������� �������:
 * GUID		            ������������� ���������� ����������(mGUID). 	[1]
 * deviceState		    ��������� ����������.                           [0..1]
 * devID                ������������� ����������                        [0..1]
 * authorizedZone       ��� ���� ����� ������������                     [1]
 * password             PIN-���                                         [0..1]
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
public class StartCreateSocialSessionRequestProcessor extends StartCreateMobileSessionRequestProcessor
{
	private static final String REQUEST_TYPE    = "startCreateSocialSessionRq";
	private static final String RESPONCE_TYPE   = "startCreateSocialSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
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

	protected MobileAuthenticationOperation instantiateAuthenticationOperation(IdentificationContext context)
	{
		return new SocialAuthenticationOperation(context);
	}

	@Override
	protected void exceptionProcessing(LogIdentificationContext logIdentificationContext, RequestInfo requestInfo) throws GateLogicException, GateException
	{};
}
