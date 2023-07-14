package com.rssl.auth.csa.back.protocol.handlers.data;

import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;

/**
 * @author tisov
 * @ created 05.03.15
 * @ $Author$
 * @ $Revision$
 * ��������������� �����, ��� ������������ ������ ������������ ��� ������������� ����� ����� ��������� ����������
 */
public class StartCreateMobileSessionData
{
	private IdentificationContext context;               //�������� �������������
	private String guid;                                 //guid
	private String deviceState;                          //��������� ����������
	private String deviceId;                             //������������� ����������
	private AuthorizedZoneType authorizedZoneType;       //��� ���� ����� ������������
	private String pin;                                  //���-���
	private String mobileSDKData;                        //������, ������� ���������� �������� � ������� ����-�����������
	private String version;                              //������ mAPI

	public StartCreateMobileSessionData(IdentificationContext context, String guid, String deviceState, String deviceId, String version, AuthorizedZoneType authorizedZoneType, String pin, String mobileSDKData)
	{
		this.context = context;
		this.guid = guid;
		this.deviceState = deviceState;
		this.deviceId = deviceId;
		this.authorizedZoneType = authorizedZoneType;
		this.pin = pin;
		this.mobileSDKData = mobileSDKData;
		this.version = version;
	}

	public IdentificationContext getContext()
	{
		return context;
	}

	public String getGuid()
	{
		return guid;
	}

	public String getDeviceState()
	{
		return deviceState;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public AuthorizedZoneType getAuthorizedZoneType()
	{
		return authorizedZoneType;
	}

	public void setAuthorizedZoneType(AuthorizedZoneType authorizedZoneType)
	{
		this.authorizedZoneType = authorizedZoneType;
	}

	public String getPin()
	{
		return pin;
	}

	public String getMobileSDKData()
	{
		return mobileSDKData;
	}

	public String getVersion()
	{
		return version;
	}
}
