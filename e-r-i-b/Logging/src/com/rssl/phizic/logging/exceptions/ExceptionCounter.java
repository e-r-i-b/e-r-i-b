package com.rssl.phizic.logging.exceptions;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * счетчик однотипных ошибок
 */

public class ExceptionCounter implements Serializable
{
	private String exceptionHash;
	private Calendar date;
	private long count;

	/**
	 * Дефолтный конструктор
	 */
	public ExceptionCounter()
	{
	}

	/**
	 * Конструктор
	 * @param exceptionHash - хеш ошибки
	 * @param date - дата на которую считаем ошибки
	 */
	public ExceptionCounter(String exceptionHash, Calendar date)
	{
		this.exceptionHash = exceptionHash;
		this.date = date;
		this.count = 1;
	}

	/**
	 * @return хеш ошибки
	 */
	public String getExceptionHash()
	{
		return exceptionHash;
	}

	/**
	 * @return дата ошибки
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @return количество ошибок
	 */
	public long getCount()
	{
		return count;
	}
}
