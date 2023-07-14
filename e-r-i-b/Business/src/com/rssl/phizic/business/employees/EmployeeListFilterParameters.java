package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Параметры фильтрации сотрудников
 */

@SuppressWarnings("ClassNameSameAsAncestorName")
public class EmployeeListFilterParameters implements com.rssl.phizic.gate.employee.EmployeeListFilterParameters
{
	private int firstResult;
	private int maxResults;

	private Long seekerLoginId;
	private String seekerLogin;
	private boolean seekerAllDepartments;
	private Long soughtId;
	private String soughtLogin;
	private String soughtFIO;
	private long soughtBlockedState = -1;
	private Calendar soughtBlockedUntil;
	private String soughtInfo;
	private String soughtTB;
	private String soughtBranchCode;
	private String soughtDepartmentCode;

	/**
	 * конструктор
	 * @param filterParams параметры фильтрации
	 * @param login логин фильтрующего записи
	 * @param allDepartments доступность всех подразделений фильтрующему
	 * @param firstResult индекс первой записи
	 * @param maxResults количество записей
	 */
	public EmployeeListFilterParameters(Map<String, Object> filterParams, BankLogin login, boolean allDepartments, int firstResult, int maxResults)
	{
		this.firstResult = firstResult;
		this.maxResults = maxResults;

		soughtId = (Long)  filterParams.get("id");
		seekerLoginId = login.getId();
		seekerLogin = login.getUserId();
		soughtLogin = StringHelper.getNullIfEmpty((String)filterParams.get("login"));
		soughtFIO = StringHelper.getNullIfEmpty((String)filterParams.get("fio"));
		String blockedString = (String) filterParams.get("blocked");
		if (StringHelper.isNotEmpty(blockedString))
			soughtBlockedState = Long.valueOf(blockedString);
		soughtBlockedUntil = Calendar.getInstance();
		soughtInfo = StringHelper.getNullIfEmpty((String)filterParams.get("info"));
		soughtTB = StringHelper.getNullIfEmpty((String)filterParams.get("terbankCode"));
		soughtBranchCode = StringHelper.getNullIfEmpty((String)filterParams.get("branchCode"));
		soughtDepartmentCode = StringHelper.getNullIfEmpty((String)filterParams.get("departmentCode"));
		seekerAllDepartments = allDepartments;
	}

	public int getFirstResult()
	{
		return firstResult;
	}

	public int getMaxResults()
	{
		return maxResults;
	}

	/**
	 * @return идентификатор логина ищущего сотрудника
	 */
	@QueryParameter
	public Long getSeekerLoginId()
	{
		return seekerLoginId;
	}

	public String getSeekerLogin()
	{
		return seekerLogin;
	}

	@QueryParameter
	public boolean isSeekerAllDepartments()
	{
		return seekerAllDepartments;
	}

	@QueryParameter
	public Long getSoughtId()
	{
		return soughtId;
	}

	@QueryParameter
	public String getSoughtLogin()
	{
		return soughtLogin;
	}

	@QueryParameter
	public String getSoughtFIO()
	{
		return soughtFIO;
	}

	@QueryParameter
	public long getSoughtBlockedState()
	{
		return soughtBlockedState;
	}

	@QueryParameter
	public Calendar getSoughtBlockedUntil()
	{
		return soughtBlockedUntil;
	}

	@QueryParameter
	public String getSoughtInfo()
	{
		return soughtInfo;
	}

	@QueryParameter
	public String getSoughtTB()
	{
		return soughtTB;
	}

	@QueryParameter
	public String getSoughtBranchCode()
	{
		return soughtBranchCode;
	}

	@QueryParameter
	public String getSoughtDepartmentCode()
	{
		return soughtDepartmentCode;
	}
}
