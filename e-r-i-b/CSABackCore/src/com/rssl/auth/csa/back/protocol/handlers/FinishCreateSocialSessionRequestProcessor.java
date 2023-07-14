package com.rssl.auth.csa.back.protocol.handlers;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ �� �������������� ������������ ����� SocialApi
 *
 * ��������� �������:
 * OUID		                    �������� �����. 	[1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * SessionInfo                  ���������� � ������                                         [0..1]
 *      SID		                ������������� �������� ������.                              [1]
 *      creationDate            ���� �������� ������                                        [1]
 *      expireDate              ���� ���������� ������ (����� ��������� ���� ����
 *                              ������� �� ������ �� ����� �����������)                     [1]
 *      prevSessionDate         ���� �������� ���������� ������                             [0..1]
 *      prevSID                 ������������� ���������� ������                             [0..1]
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 * ConnectorInfo                ���������� � ����������                                     [0..1]
 *      GUID                    ������������� ����������                                     [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
 *      devID                   ������������� ����������                                    [0..1]
 * authorizedZone               ��� ���� ����� ������������                                 [1]
 */
public class FinishCreateSocialSessionRequestProcessor extends FinishCreateMobileSessionRequestProcessor
{
	private static final String REQUEST_TYPE    = "finishCreateSocialSessionRq";
	private static final String RESPONSE_TYPE   = "finishCreateSocialSessionRs";

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
}
