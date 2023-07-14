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
	 * ����� �� ����� ������
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Employee findByUserId(String userId) throws BusinessException
	{
	    return findByUserId(userId, null);
	}


	/**
	 * ����� ���������� ����� �� ��� ������
	 * @param login ����� ���������� �����
	 * @return ��������� �����
	 */
	public Employee findByLogin(BankLogin login) throws BusinessException
	{
		return findByLogin(login, null);
	}

	/**
	 * ������� ������ � ������������ ���������.
	 * @param employee ���������
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
	 * ������, ����� �� ��������� ������ � ������������ � ����������� tb, osb, vsp
	 * @param loginId - ������������� ������ ����������
	 * @param tb    - ��
	 * @param osb   - ���
	 * @param vsp   - ���
	 * @return false - ��� ������� � �������������, true - ����
	 * @throws BusinessException
	 */
	public Boolean isDepartmentsAllowedByCode(final Long loginId, String tb, String osb, String vsp) throws BusinessException
	{
		return super.isDepartmentsAllowedByCode(loginId, tb, osb, vsp, null);
	}

	/**
	 * ������, ����� �� ��������� ������ �� ���� �� �� ������,
	 * ���� ����� tbs ���� id ������������, ������� �� �������� ���������, �� ������� ����� false
	 * @param employeeLoginId - ������������� ����������
	 * @param tbs - ������ ������ ���������
	 * @return false - ��� ������� � ����������, true - ����
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
	 * ������� ������ � ��������� �� managerId
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
