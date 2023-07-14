package com.rssl.phizic.logging.extendedLogging;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author gladishev
 * @ created 07.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ����������� �����������
 */
public class ClientExtendedLoggingEntry implements Serializable
{
	private Long loginId; //������������� ������ (�� �� ������������� ������)
	private Calendar startDate; //����� ������ ������������ �����������
	private Calendar endDate;   //����� ��������� ������������ �����������

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
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
}
