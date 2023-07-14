package com.rssl.phizic.logging.exceptions;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���������� ������
 */

public class ExceptionCounter implements Serializable
{
	private String exceptionHash;
	private Calendar date;
	private long count;

	/**
	 * ��������� �����������
	 */
	public ExceptionCounter()
	{
	}

	/**
	 * �����������
	 * @param exceptionHash - ��� ������
	 * @param date - ���� �� ������� ������� ������
	 */
	public ExceptionCounter(String exceptionHash, Calendar date)
	{
		this.exceptionHash = exceptionHash;
		this.date = date;
		this.count = 1;
	}

	/**
	 * @return ��� ������
	 */
	public String getExceptionHash()
	{
		return exceptionHash;
	}

	/**
	 * @return ���� ������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @return ���������� ������
	 */
	public long getCount()
	{
		return count;
	}
}
