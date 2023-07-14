package com.rssl.phizic.messaging.push;

import com.rssl.phizic.logging.push.PushDeviceStatus;

import java.util.Calendar;

/**
 * Push ����������� �� ���������� ������� ������������ ����������� ������������ �������
 * @author basharin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PushQueueDevice
{
	private Long id;
	private Calendar timestamp;                         //����� �������� ���������
	private String deviceId;                            //������������� ����������, ������������ ��� ��������� push-�����������
	private String clientId;                            //������������� �������
	private PushDeviceStatus status;                     //������ ����������� push ����������� � ����������
	private String securityToken;                       //������ ������ ������������, �������������� ��������� �����������
	private PushProcStatus procStatus;                  //��������� ���������
	private Calendar procStatusAt;                      //����� ���������� ��������� procStatus ���������
	private String procError;                           //�������� ������
	private String mguidHash;                               //Hash ������� SHA-1 �� �������� MGUID

	String getClientId()
	{
		return clientId;
	}

	void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	String getDeviceId()
	{
		return deviceId;
	}

	void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	@Override public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		addIfNotEmpty(stringBuilder, "id", id);
		addIfNotEmpty(stringBuilder, "timestamp", timestamp);
		addIfNotEmpty(stringBuilder, "device_id", deviceId);
		addIfNotEmpty(stringBuilder, "client_id", clientId);
		addIfNotEmpty(stringBuilder, "status", status);
		addIfNotEmpty(stringBuilder, "security_token", securityToken);
		addIfNotEmpty(stringBuilder, "proc_status", procStatus);
		addIfNotEmpty(stringBuilder, "proc_status_at", procStatusAt);
		addIfNotEmpty(stringBuilder, "proc_error", procError);
		addIfNotEmpty(stringBuilder, "mguid", mguidHash);
		return stringBuilder.toString();
	}

	private void addIfNotEmpty(StringBuilder stringBuilder, String key, Object value)
	{
		if (value != null)
			stringBuilder.append(key + "-" + value.toString() + ";");
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp)
	{
		this.timestamp = timestamp;
	}

	public PushDeviceStatus getStatus()
	{
		return status;
	}

	public void setStatus(PushDeviceStatus status)
	{
		this.status = status;
	}

	public String getSecurityToken()
	{
		return securityToken;
	}

	public void setSecurityToken(String securityToken)
	{
		this.securityToken = securityToken;
	}

	String getMguidHash()
	{
		return mguidHash;
	}

	void setMguidHash(String mguid)
	{
		this.mguidHash = mguid;
	}

	public PushProcStatus getProcStatus()
	{
		return procStatus;
	}

	public void setProcStatus(PushProcStatus procStatus)
	{
		this.procStatus = procStatus;
	}

	public Calendar getProcStatusAt()
	{
		return procStatusAt;
	}

	public void setProcStatusAt(Calendar procStatusAt)
	{
		this.procStatusAt = procStatusAt;
	}

	public String getProcError()
	{
		return procError;
	}

	public void setProcError(String procError)
	{
		this.procError = procError;
	}
}
