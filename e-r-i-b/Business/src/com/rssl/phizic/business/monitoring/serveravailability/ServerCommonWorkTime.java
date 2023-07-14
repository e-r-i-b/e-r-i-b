package com.rssl.phizic.business.monitoring.serveravailability;

import java.util.Calendar;

/**
 * ����� ����� ������ ��
 * @author gladishev
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ServerCommonWorkTime
{
	private String serverId;     //������������� �������
	private Calendar startDate;   //���� ������ ������ ��
	private Calendar endDate;    //���� ��������� ������ ��
	private Calendar endPingDate;    //���� ���������� �������� �����

	public String getServerId()
	{
		return serverId;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Calendar getEndPingDate()
	{
		return endPingDate;
	}

	public void setEndPingDate(Calendar endPingDate)
	{
		this.endPingDate = endPingDate;
	}
}
