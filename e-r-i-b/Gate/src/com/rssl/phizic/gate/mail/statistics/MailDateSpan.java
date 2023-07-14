package com.rssl.phizic.gate.mail.statistics;

import com.rssl.phizic.utils.DateHelper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Формирует строку вида 1 дн. 2ч. 3мин. по миллисекундам
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
	 * Дефолтный конструктор для хибернейта
	 */
	public MailDateSpan()
	{
	}

	/**
	 * Конструктор
	 * @param milliseconds - время в миллисекундах
	 * @param count - количество данных, на которых собрана статистика
	 */
	public MailDateSpan(Long milliseconds, Long count)
	{
		this.milliseconds = milliseconds;
		this.count = count;
	}

	/**
	 * @return миллисекунды
	 */
	public Long getMilliseconds()
	{
		return milliseconds;
	}

	/**
	 * @param milliseconds миллисекунды
	 */
	public void setMilliseconds(Long milliseconds)
	{
		calendar.setTimeInMillis(milliseconds);
		this.milliseconds = milliseconds;
	}

	/**
	 * @return дни
	 */
	public long getDays()
	{
		return milliseconds/(DateHelper.MILLISECONDS_IN_DAY);
	}

	/**
	 * @return часы
	 */
	public long getHours()
	{

		return calendar.get(Calendar.HOUR);
	}

	/**
	 * @return минуты
	 */
	public long getMinutes()
	{
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * @return секунды
	 */
	public long getSeconds()
	{
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * @return количество данных, на которых собрана статистика
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * @param count количество данных, на которых собрана статистика
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}

	/**
	 * @return идентификатор блока, для которого собрана статистика
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId идентификатор блока, для которого собрана статистика
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
			s.append("дн. ");
		}

		s.append(getHours());
		s.append("ч. ");
		s.append(getMinutes());
		s.append("мин. ");

		if (days <= 0)
		{
			s.append(getSeconds());
			s.append("cек.");
		}

		return s.toString();
	}
}
