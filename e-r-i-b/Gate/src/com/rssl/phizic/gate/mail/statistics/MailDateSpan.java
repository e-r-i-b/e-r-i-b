package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.utils.DateHelper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * ��������� ������ ���� 1 ��. 2�. 3���. �� �������������
 * @author komarov
 * @ created 16.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class MailDateSpan implements Serializable
{
	private Long milliseconds;
	private Long count;
	private Long nodeId;
	private Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-0:00"));

	/**
	 * ��������� ����������� ��� ����������
	 */
	public MailDateSpan()
	{
	}

	/**
	 * �����������
	 * @param milliseconds - ����� � �������������
	 * @param count - ���������� ������, �� ������� ������� ����������
	 */
	public MailDateSpan(Long milliseconds, Long count)
	{
		this.milliseconds = milliseconds;
		this.count = count;
	}

	/**
	 * @return ������������
	 */
	public Long getMilliseconds()
	{
		return milliseconds;
	}

	/**
	 * @param milliseconds ������������
	 */
	public void setMilliseconds(Long milliseconds)
	{
		calendar.setTimeInMillis(milliseconds);
		this.milliseconds = milliseconds;
	}

	/**
	 * @return ���
	 */
	public long getDays()
	{
		return milliseconds/(DateHelper.MILLISECONDS_IN_DAY);
	}

	/**
	 * @return ����
	 */
	public long getHours()
	{

		return calendar.get(Calendar.HOUR);
	}

	/**
	 * @return ������
	 */
	public long getMinutes()
	{
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @return �������
	 */
	public long getSeconds()
	{
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * @return ���������� ������, �� ������� ������� ����������
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * @param count ���������� ������, �� ������� ������� ����������
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}

	/**
	 * @return ������������� �����, ��� �������� ������� ����������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId ������������� �����, ��� �������� ������� ����������
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	@Override
	public String toString()
	{
		StringBuilder s  = new StringBuilder();
		long days = getDays();
		if(days > 0)
		{
			s.append(days);
			s.append("��. ");
		}

		s.append(getHours());
		s.append("�. ");
		s.append(getMinutes());
		s.append("���. ");

		if (days <= 0)
		{
			s.append(getSeconds());
			s.append("c��.");
		}

		return s.toString();
	}
}
