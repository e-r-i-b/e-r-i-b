package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeService extends MultiInstanceEmployeeService
{
    public void remove(final Employee employee) throws BusinessException
    {
        super.remove(employee, null);
    }

    public Employee add(Employee employee) throws BusinessException
    {
        return super.add(employee, null);
    }

    public Employee update(Employee employee) throws BusinessException
    {
        return super.update(employee, null);
    }

    public Employee findById(Long id) throws BusinessException
    {
        return super.findById(id, null);
    }

	/**
	 * Поиск по имени логина
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Employee findByUserId(String userId) throws BusinessException
	{
	    return findByUserId(userId, null);
	}


	/**
	 * Найти сотрудника банка по его логину
	 * @param login логин сотрудника банка
	 * @return сотрудник банка
	 */
	public Employee findByLogin(BankLogin login) throws BusinessException
	{
		return findByLogin(login, null);
	}

	/**
	 * Очищает данные о персональном менеджере.
	 * @param employee сотрудник
	 * @throws BusinessException
	 */
	public void clearManagerInfo(Employee employee) throws BusinessException
	{
		super.clearManagerInfo(employee.getId(), null);
	}

	public void removeAllowedDepartments(Employee employee) throws BusinessException
	{
	 	super.removeAllowedDepartments(employee, null);
	}

	public void  replicateEmployeeDepartment(Employee employee) throws BusinessException
	{
		super.replicateEmployeeDepartment(employee, null);
	}

	public void replicateDepartments(Employee employee, Employee parentEmployee) throws BusinessException
	{
		super.replicateDepartments(employee, parentEmployee, null);
	}

	public Boolean isDepartmentsAllowed(final Long loginId, final List<Long> departments) throws BusinessException
	{
		return super.isDepartmentsAllowed(loginId, departments, null);
	}

	/**
	 * Узнаем, имеет ли сотрудник доступ к подразделеню с переданными tb, osb, vsp
	 * @param loginId - идентификатор логина сотрдуника
	 * @param tb    - ТБ
	 * @param osb   - ОСБ
	 * @param vsp   - ВСП
	 * @return false - нет доступа к подразделению, true - есть
	 * @throws BusinessException
	 */
	public Boolean isDepartmentsAllowedByCode(final Long loginId, String tb, String osb, String vsp) throws BusinessException
	{
		return super.isDepartmentsAllowedByCode(loginId, tb, osb, vsp, null);
	}

	/**
	 * Узнаем, имеет ли сотрудник доступ ко всем ТБ из списка,
	 * если среди tbs есть id департамента, который не является тербанком, то функция вернёт false
	 * @param employeeLoginId - идентификатор сотрдуника
	 * @param tbs - список номров Тербанков
	 * @return false - нет доступа к сотруднику, true - есть
	 * @throws BusinessException
	 */
	public Boolean isTBsAllowed(final Long employeeLoginId, final Collection<String> tbs) throws BusinessException
	{
		return super.isTBsAllowed(employeeLoginId, tbs, null);
	}

	public void updateAllowedDepartmentByGate(final Employee employee, final List<com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment> gateAllowedDepartments) throws BusinessException
	{
		super.updateAllowedDepartmentByGate(employee,gateAllowedDepartments,null);
	}

	/**
	 * Удаляет данные о менеджере по managerId
	 * @param id managerId
	 * @throws BusinessException
	 */
	public void clearManagerInfo(final String id) throws BusinessException
	{
		if (StringHelper.isEmpty(id))
			return;
		try
		{
			HibernateExecutor.getInstance(null).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(Employee.class.getName() + ".clearManagerInfoByManagerId");
					query.setParameter("managerId", id);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
