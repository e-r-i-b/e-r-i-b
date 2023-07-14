package com.rssl.phizic.messaging.push;

import java.util.Calendar;

/**
 * ���������� �� ������ �������� ������� �� �������� ����� ������� ��� � ������ ������� �������� push-�����������
 * @author basharin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PushAddress
{
	private Long id;
	private Calendar timestamp;                         //����� �������� ���������
	private PushEvent pushEvent;                        //��������� ���������
	private String address;                             //����� �������� �������, �� ������� ����� ���������� ���, � ������ ���� push-����������� �� ���������� �� �� ���� ����������
	private PushProcStatus procStatus;                  //��������� ���������
	private Calendar procStatusAt;                      //����� ���������� ��������� procStatus ���������
	private String procError;                           //�������� ������

	String getAddress()
	{
		return address;
	}

	void setAddress(String address)
	{
		this.address = address;
	}

	PushEvent getPushEvent()
	{
		return pushEvent;
	}

	void setPushEvent(PushEvent pushEvent)
	{
		this.pushEvent = pushEvent;
	}

	@Override public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		addIfNotEmpty(stringBuilder, "id", id);
		addIfNotEmpty(stringBuilder, "timestamp", timestamp);
		addIfNotEmpty(stringBuilder, "address", address);
		addIfNotEmpty(stringBuilder, "proc_status", procStatus);
		addIfNotEmpty(stringBuilder, "proc_status_at", procStatusAt);
		addIfNotEmpty(stringBuilder, "proc_error", procError);
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
