package com.rssl.phizic.gate.employee;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 14.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Параметры фильтрации сотрудников
 */

public interface EmployeeListFilterParameters
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
	 * @return логин ищущего сотрудника
	 */
	public String getSeekerLogin();

	/**
	 * @return признак доступности поиска по всем подразделениям
	 */
    public boolean isSeekerAllDepartments();

	/**
	 * @return идентификатор искомого сотрудника
	 */
    public Long getSoughtId();

	/**
	 * @return логин искомого сотрудника
	 */
    public String getSoughtLogin();

	/**
	 * @return ФИО искомого сотрудника
	 */
    public String getSoughtFIO();

	/**
	 * @return признак заблокированности искомых сотрудников
	 */
    public long getSoughtBlockedState();

	/**
	 * @return дата окончания блокировки искомых сотрудников
	 */
    public Calendar getSoughtBlockedUntil();

	/**
	 * @return доп. информация искомых сотрудников
	 */
    public String getSoughtInfo();

	/**
	 * @return код тербанка искомых сотрудников
	 */
    public String getSoughtTB();

	/**
	 * @return код ОСБ искомых сотрудников
	 */
    public String getSoughtBranchCode();

	/**
	 * @return код ВСП искомых сотрудников
	 */
	public String getSoughtDepartmentCode();
}
