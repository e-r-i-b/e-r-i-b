package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 02.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования доступных подразделений сотрудника
 */

public class EditAllowedDepartmentsOperation extends EditEmployeeOperation
{
	private static final String ALLOWED_QUERY_NAME = "allowed";
	private static final String ADMIN_LOGIN_ID_QUERY_PARAMETER = "adminLoginId";
	private static final String ALLOWED_DEPARTMENTS_QUERY_PARAMETER = "allowedDepartments";
	private static final String KEY_DELIMITER = "|";
	private static final String ALL_DEPARTMENT_KEY = "*";


	private List<String> getAllowedKeys(List<AllowedDepartment> allowedDepartmentsForEmployee)
	{
		List<String> result = new ArrayList<String>(allowedDepartmentsForEmployee.size());
		for (AllowedDepartment allowedDepartment : allowedDepartmentsForEmployee)
		{
			String key = allowedDepartment.getTb().concat(KEY_DELIMITER);
			if (!ALL_DEPARTMENT_KEY.equals(allowedDepartment.getOsb()))
				key = key.concat(allowedDepartment.getOsb());
			key = key.concat(KEY_DELIMITER);
			if (!ALL_DEPARTMENT_KEY.equals(allowedDepartment.getVsp()))
				key = key.concat(allowedDepartment.getVsp());
			result.add(key);
		}
		return result;
	}

	private Long getAdminLoginId()
	{
		return getOwner().getLogin().getId();
	}

	private List<AllowedDepartmentWrapper> getAllTBAllowedRecords() throws BusinessException
	{
		return Collections.singletonList(new AllowedDepartmentWrapper(ALL_DEPARTMENT_KEY, ALL_DEPARTMENT_KEY, ALL_DEPARTMENT_KEY, "Все ТБ", isAdminHasAllTBAccess()));
	}

	private List<AllowedDepartmentWrapper> getAllowedDepartments(List<AllowedDepartment> allowedDepartments) throws BusinessException
	{
		if (CollectionUtils.isEmpty(allowedDepartments))
			return Collections.emptyList();

		if (allowedDepartments.size() == 1)
		{
			AllowedDepartment allowedDepartment = allowedDepartments.get(0);
			if (ALL_DEPARTMENT_KEY.equals(allowedDepartment.getTb()))
				return getAllTBAllowedRecords();
		}

		try
		{
			Query query = createQuery(ALLOWED_QUERY_NAME);
			query.setParameter(ADMIN_LOGIN_ID_QUERY_PARAMETER, getAdminLoginId());
			query.setParameterList(ALLOWED_DEPARTMENTS_QUERY_PARAMETER, getAllowedKeys(allowedDepartments));
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return имеет ли редактирующий сотрудник доступ ко всем подразделениям
	 */
	public boolean isAdminHasAllTBAccess()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess();
	}

	/**
	 * @return список доступных подразделений
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public List<AllowedDepartmentWrapper> getAllowedDepartments() throws BusinessException, BusinessLogicException
	{
		try
		{
			List<AllowedDepartment> allowedDepartmentsForEmployee = getService().getAllowedDepartments(getEntity());
			return getAllowedDepartments(allowedDepartmentsForEmployee);
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

	/**
	 * Работа с доступом сотрудника к подразделениям
	 * @param addAllowedDepartments - доступные подразделения для добавления
	 * @param removeAllowedDepartments - доступные подразделения для удаления
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void saveAllowedDepartments(List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws BusinessException, BusinessLogicException
	{
		try
		{
			//noinspection unchecked
			checkAccess(ListUtils.sum(addAllowedDepartments, removeAllowedDepartments));
			getService().saveAllowedDepartments(getEntity(), addAllowedDepartments, removeAllowedDepartments);
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

	private void checkAccess(List<AllowedDepartment> allowedDepartments) throws BusinessException, BusinessLogicException
	{
		for (AllowedDepartmentWrapper allowedDepartmentWrapper : getAllowedDepartments(allowedDepartments))
		{
			if (!allowedDepartmentWrapper.isAllowed())
				throw new BusinessLogicException("Вы не имеете доступа к подразделению " + allowedDepartmentWrapper.getName());
		}
	}
}
