package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.dataaccess.query.QueryParameter;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ѕараметры фильтрации
 */

public class ContactCenterEmployeeFilterParameters
{
	private int firstResult;
	private int maxResults;

	private Calendar soughtBlockedUntil;
	private Long soughtId;
	private String soughtFIO;
	private String soughtArea;

	/**
	 * @return индекс первой записи
	 */
	public int getFirstResult()
	{
		return firstResult;
	}

	/**
	 * задать индекс первой записи
	 * @param firstResult индекс первой записи
	 */
	public void setFirstResult(int firstResult)
	{
		this.firstResult = firstResult;
	}

	/**
	 * @return количество записей
	 */
	public int getMaxResults()
	{
		return maxResults;
	}

	/**
	 * задать количество записей
	 * @param maxResults количество записей
	 */
	public void setMaxResults(int maxResults)
	{
		this.maxResults = maxResults;
	}

	/**
	 * @return дата окончани€ блокировки искомых сотрудников
	 */
	@QueryParameter
	public Calendar getSoughtBlockedUntil()
	{
		return soughtBlockedUntil;
	}

	/**
	 * задать дату окончани€ блокировки искомых сотрудников
	 * @param soughtBlockedUntil дата окончани€ блокировки искомых сотрудников
	 */
	public void setSoughtBlockedUntil(Calendar soughtBlockedUntil)
	{
		this.soughtBlockedUntil = soughtBlockedUntil;
	}

	/**
	 * @return идентификатор искомого сотрудника
	 */
	@QueryParameter
	public Long getSoughtId()
	{
		return soughtId;
	}

	/**
	 * задать идентификатор искомого сотрудника
	 * @param soughtId идентификатор искомого сотрудника
	 */
	public void setSoughtId(Long soughtId)
	{
		this.soughtId = soughtId;
	}

	/**
	 * @return ‘»ќ искомого сотрудника
	 */
	@QueryParameter
	public String getSoughtFIO()
	{
		return soughtFIO;
	}

	/**
	 * задать ‘»ќ искомого сотрудника
	 * @param soughtFIO ‘»ќ искомого сотрудника
	 */
	public void setSoughtFIO(String soughtFIO)
	{
		this.soughtFIO = soughtFIO;
	}

	/**
	 * @return площадка искомого сотрудника
	 */
	@QueryParameter
	public String getSoughtArea()
	{
		return soughtArea;
	}

	/**
	 * задать площадку искомого сотрудника
	 * @param soughtArea площадка
	 */
	public void setSoughtArea(String soughtArea)
	{
		this.soughtArea = soughtArea;
	}
}
