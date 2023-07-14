package com.rssl.phizic.operations.employees.GetEmployeeList;

import com.rssl.phizic.gate.employee.ContactCenterEmployeeFilterParameters;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * параметры фильтрации
 */

public class ContactCenterEmployeeFilterParametersImpl implements ContactCenterEmployeeFilterParameters
{
	private final int firstResult;
	private final int maxResults;

	private Calendar soughtBlockedUntil;
	private final Long soughtId;
	private final String soughtFIO;
	private final String soughtArea;

	/**
	 * конструктор
	 * @param soughtId     идентификатор искомого сотрудника
	 * @param soughtFIO    ФИО искомого сотрудника
	 * @param soughtArea   площадка искомого сотрудника
	 * @param firstResult  дата окончания блокировки искомых сотрудников
	 * @param soughtBlockedUntil  индекс первой записи
	 * @param maxResults   количество записей
	 */
	public ContactCenterEmployeeFilterParametersImpl(Long soughtId, String soughtFIO, String soughtArea, Calendar soughtBlockedUntil, int firstResult, int maxResults)
	{
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.soughtBlockedUntil = soughtBlockedUntil;
		this.soughtId = soughtId;
		this.soughtFIO = soughtFIO;
		this.soughtArea = soughtArea;
	}

	public int getFirstResult()
	{
		return firstResult;
	}

	public int getMaxResults()
	{
		return maxResults;
	}

	public Calendar getSoughtBlockedUntil()
	{
		return soughtBlockedUntil;
	}

	public Long getSoughtId()
	{
		return soughtId;
	}

	public String getSoughtFIO()
	{
		return soughtFIO;
	}

	public String getSoughtArea()
	{
		return soughtArea;
	}
}
