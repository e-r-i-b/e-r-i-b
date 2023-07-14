package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.dataaccess.query.QueryParameter;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 *
 * параметры фильтрации
 */

public class EmployeeListFilterParameters
{
	private int firstResult;
	private int maxResults;
	private Long seekerLoginId;
	private boolean seekerAllDepartments;
	private Long soughtId;
	private String soughtLogin;
	private String soughtFIO;
	private long soughtBlockedState;
	private Calendar soughtBlockedUntil;
	private String soughtInfo;
	private String soughtTB;
	private String soughtBranchCode;
	private String soughtDepartmentCode;

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
	 * @return логин ищущего сотрудника
	 */
	@QueryParameter
	public Long getSeekerLoginId()
	{
		return seekerLoginId;
	}

	/**
	 * задать логин ищущего сотрудника
	 * @param seekerLoginId логин ищущего сотрудника
	 */
	public void setSeekerLoginId(Long seekerLoginId)
	{
		this.seekerLoginId = seekerLoginId;
	}

	/**
	 * @return признак доступности поиска по всем подразделениям
	 */
	@QueryParameter
	public boolean isSeekerAllDepartments()
	{
		return seekerAllDepartments;
	}

	/**
	 * задать признак доступности поиска по всем подразделениям
	 * @param seekerAllDepartments признак доступности поиска по всем подразделениям
	 */
	public void setSeekerAllDepartments(boolean seekerAllDepartments)
	{
		this.seekerAllDepartments = seekerAllDepartments;
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
	 * @return логин искомого сотрудника
	 */
	@QueryParameter
	public String getSoughtLogin()
	{
		return soughtLogin;
	}

	/**
	 * задать логин искомого сотрудника
	 * @param soughtLogin логин искомого сотрудника
	 */
	public void setSoughtLogin(String soughtLogin)
	{
		this.soughtLogin = soughtLogin;
	}

	/**
	 * @return ФИО искомого сотрудника
	 */
	@QueryParameter
	public String getSoughtFIO()
	{
		return soughtFIO;
	}

	/**
	 * задать ФИО искомого сотрудника
	 * @param soughtFIO ФИО искомого сотрудника
	 */
	public void setSoughtFIO(String soughtFIO)
	{
		this.soughtFIO = soughtFIO;
	}

	/**
	 * @return признак заблокированности искомых сотрудников
	 */
	@QueryParameter
	public long getSoughtBlockedState()
	{
		return soughtBlockedState;
	}

	/**
	 * задать признак заблокированности искомых сотрудников
	 * @param soughtBlockedState признак заблокированности искомых сотрудников
	 */
	public void setSoughtBlockedState(long soughtBlockedState)
	{
		this.soughtBlockedState = soughtBlockedState;
	}

	/**
	 * @return дата окончания блокировки искомых сотрудников
	 */
	@QueryParameter
	public Calendar getSoughtBlockedUntil()
	{
		return soughtBlockedUntil;
	}

	/**
	 * задать дату окончания блокировки искомых сотрудников
	 * @param soughtBlockedUntil дата окончания блокировки искомых сотрудников
	 */
	public void setSoughtBlockedUntil(Calendar soughtBlockedUntil)
	{
		this.soughtBlockedUntil = soughtBlockedUntil;
	}

	/**
	 * @return доп. информация искомых сотрудников
	 */
	@QueryParameter
	public String getSoughtInfo()
	{
		return soughtInfo;
	}

	/**
	 * задать доп. информацию искомых сотрудников
	 * @param soughtInfo доп. информация искомых сотрудников
	 */
	public void setSoughtInfo(String soughtInfo)
	{
		this.soughtInfo = soughtInfo;
	}

	/**
	 * @return код тербанка искомых сотрудников
	 */
	@QueryParameter
	public String getSoughtTB()
	{
		return soughtTB;
	}

	/**
	 * задать код тербанка искомых сотрудников
	 * @param soughtTB код тербанка
	 */
	public void setSoughtTB(String soughtTB)
	{
		this.soughtTB = soughtTB;
	}

	/**
	 * @return код ОСБ искомых сотрудников
	 */
	@QueryParameter
	public String getSoughtBranchCode()
	{
		return soughtBranchCode;
	}

	/**
	 * задать код ОСБ искомых сотрудников
	 * @param soughtBranchCode код ОСБ
	 */
	public void setSoughtBranchCode(String soughtBranchCode)
	{
		this.soughtBranchCode = soughtBranchCode;
	}

	/**
	 * @return код ВСП искомых сотрудников
	 */
	@QueryParameter
	public String getSoughtDepartmentCode()
	{
		return soughtDepartmentCode;
	}

	/**
	 * задать код ВСП искомых сотрудников
	 * @param soughtDepartmentCode код ВСП
	 */
	public void setSoughtDepartmentCode(String soughtDepartmentCode)
	{
		this.soughtDepartmentCode = soughtDepartmentCode;
	}
}
