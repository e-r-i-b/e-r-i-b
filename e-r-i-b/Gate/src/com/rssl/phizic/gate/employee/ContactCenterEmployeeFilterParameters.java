package com.rssl.phizic.gate.employee;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * параметры фильтрации
 */

public interface ContactCenterEmployeeFilterParameters
{
	/**
	 * @return индекс первой записи
	 */
	public int getFirstResult();

	/**
	 * @return количество записей
	 */
	public int getMaxResults();

	/**
	 * @return дата окончани€ блокировки искомых сотрудников
	 */
	public Calendar getSoughtBlockedUntil();

	/**
	 * @return идентификатор искомого сотрудника
	 */
	public Long getSoughtId();

	/**
	 * @return ‘»ќ искомого сотрудника
	 */
	public String getSoughtFIO();

	/**
	 * @return площадка искомого сотрудника
	 */
	public String getSoughtArea();
}
