package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.common.types.csa.AuthorizedZoneType;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ������ ����� ������������ ����� socialAPI
 */
public class StartCreateSocialSessionRequestData extends StartCreateMobileSessionRequestData
{
	private static final String REQUEST_DATA_NAME = "startCreateSocialSessionRq";

	/**
	 * �����������
	 * @param guid ������������� ����������
	 * @param deviceState ��������� ����������
	 * @param deviceId ������������� ����������
	 * @param authorizedZoneType ��� ���� ����� ������������
	 */
	public StartCreateSocialSessionRequestData(String guid, String deviceState, String deviceId, AuthorizedZoneType authorizedZoneType, String pin)
	{
		super(guid, deviceState, deviceId, null, authorizedZoneType, pin, null);
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}
}
