package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.employees.EmployeeListFilterParameters;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.dataaccess.query.CustomExecutorQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;
import com.rssl.phizic.operations.employees.GetEmployeeList.ContactCenterEmployeeListSOAPExecutor;

import java.util.List;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetEmployeeListOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{
	public static final String CONTACT_CENTER_EMPLOYEE_LIST_QUERY_NAME = "contactCenterEmployeeList";

	public static final String BLOCKED_UNTIL_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER = "blockedUntil";
	public static final String EMPLOYEE_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER = "employeeId";
	public static final String FIO_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER = "fio";
	public static final String AREA_ID_CONTACT_CENTER_EMPLOYEE_LIST_QUERY_PARAMETER = "area_id";

	private static final ContactCenterAreaService areaService = new ContactCenterAreaService();

	/**
	 * @return список доступных тербанков
	 * @throws BusinessException
	 */
	public List<Department> getAllowedTB() throws BusinessException
	{
		return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();
	}

	/**
	 * @param parameters параметры поиска
	 * @return список сотрудников
	 * @throws BusinessException
	 */
	public List<com.rssl.phizic.gate.employee.Employee> getAll(EmployeeListFilterParameters parameters) throws BusinessException, BusinessLogicException
	{
		try
		{
			return GateSingleton.getFactory().service(EmployeeService.class).getList(parameters);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	public Query createQuery(String name)
	{
		if (CONTACT_CENTER_EMPLOYEE_LIST_QUERY_NAME.equals(name) && MultiBlockModeDictionaryHelper.isMailMultiBlockMode())
			return new CustomExecutorQuery(this, new ContactCenterEmployeeListSOAPExecutor());
		return super.createQuery(name);
	}

	/**
	 * Возвращает все площадки КЦ
	 * @return список площадок
	 * @throws BusinessException
	 */
	public List<ContactCenterArea> getAreas() throws BusinessException
	{
		return areaService.getAreas(MultiBlockModeDictionaryHelper.isMailMultiBlockMode()? MultiBlockModeDictionaryHelper.getDictionaryLogInstanceName(): null);
	}
}
