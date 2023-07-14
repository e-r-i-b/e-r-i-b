package com.rssl.phizic.business.clients.list;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �������
 */

public class ClientBlock
{
	private Calendar from;
	private Calendar to;
	private String reason;
	private String blockerFIO;

	/**
	 * @return ������ ����������
	 */
	public Calendar getFrom()
	{
		return from;
	}

	/**
	 * ������ ������ ����������
	 * @param from ������ ����������
	 */
	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	/**
	 * @return ��������� ����������
	 */
	public Calendar getTo()
	{
		return to;
	}

	/**
	 * ������ ��������� ����������
	 * @param to ��������� ����������
	 */
	public void setTo(Calendar to)
	{
		this.to = to;
	}

	/**
	 * @return ������� ����������
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * ������ ������� ����������
	 * @param reason ������� ����������
	 */
	public void setReason(String reason)
	{
		this.reason = reason;
	}

	/**
	 * @return ��� ����������������
	 */
	public String getBlockerFIO()
	{
		return blockerFIO;
	}

	/**
	 * ������ ��� ����������������
	 * @param blockerFIO ��� ����������������
	 */
	public void setBlockerFIO(String blockerFIO)
	{
		this.blockerFIO = blockerFIO;
	}
}
