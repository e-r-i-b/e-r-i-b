package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.gate.multinodes.JoinMultiNodeEntity;

/**
 * �������� ���������� ��������� ��������� ��������
 * @author komarov
 * @ created 15.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class MailStatisticsRecord implements JoinMultiNodeEntity<MailStatisticsRecord>
{
	private String state;
	private Long counter;

	/**
	 * @return ������, ��� �������� ������� ����������
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * ���������� ������, ��� �������� ������� ����������
	 * @param state ������, ��� �������� ������� ����������
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return ���������� ���� ����������
	 */
	public Long getCounter()
	{
		return counter;
	}

	/**
	 * ���������� ����������
	 * @param counter - ����������
	 */
	public void setCounter(Long counter)
	{
		this.counter = counter;
	}

	public void join(MailStatisticsRecord anotherObject)
	{
		this.counter = this.counter + anotherObject.counter;
	}

	public int compareTo(MailStatisticsRecord o)
	{
		if(o == null)
			return 1;
		return this.state.compareTo(o.getState());
	}
}
