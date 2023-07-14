package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.gate.multinodes.JoinMultiNodeEntity;

/**
 * Сущность статистика обработки обращений клиентов
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
	 * @return статус, для которого собрана статистика
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Установить статус, для которого собрана статистика
	 * @param state статус, для которого собрана статистика
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return собственно сама статистика
	 */
	public Long getCounter()
	{
		return counter;
	}

	/**
	 * Установить статистику
	 * @param counter - статистика
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
