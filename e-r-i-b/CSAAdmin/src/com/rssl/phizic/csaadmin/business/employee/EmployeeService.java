package com.rssl.phizic.csaadmin.business.employee;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������������
 */

public class EmployeeService extends SimpleService<Employee>
{
	private static final String DEFAULT = "NULL";
	private static final String LIST_QUERY_NAME = "com.rssl.phizic.csaadmin.business.employee.queries.list";
	private static final String MAIL_MANAGER_LIST_QUERY_NAME = "com.rssl.phizic.csaadmin.business.employee.queries.mailManagerList";
	private static final String CHECK_ALLOWED_QUERY_NAME = "com.rssl.phizic.csaadmin.business.employee.queries.checkAllowed";
	private static final String GET_NUMBER_ADMINS_QUERY_NAME = "com.rssl.phizic.csaadmin.business.employee.queries.getNumberAdmins";

	@Override
	protected Class<Employee> getEntityClass()
	{
		return Employee.class;
	}

	/**
	 * ������ �����������
	 * @param parameters ��������� ������
	 * @return ������ �����������
	 * @throws AdminException
	 */
	public List<Employee> getList(EmployeeListFilterParameters parameters) throws AdminException
	{
		try
		{
			BeanQuery query = new BeanQuery(parameters, LIST_QUERY_NAME, getInstanceName());
			query.setMaxResults(parameters.getMaxResults());
			query.setFirstResult(parameters.getFirstResult());
			return query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * ������ ����������� ����������� ������
	 * @param parameters ��������� ������
	 * @return ������ �����������
	 * @throws AdminException
	 */
	public List<ContactCenterEmployee> getMailManagerList(ContactCenterEmployeeFilterParameters parameters) throws AdminException
	{
		try
		{
			BeanQuery query = new BeanQuery(parameters, MAIL_MANAGER_LIST_QUERY_NAME, getInstanceName());
			query.setMaxResults(parameters.getMaxResults());
			query.setFirstResult(parameters.getFirstResult());
			return query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new AdminException(e);
		}
	}

	/**
	 * ����� ���������� �� ������
	 * @param login - �����
	 * @return ���������
	 * @throws AdminException
	 */
	public Employee findByLogin(Login login) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		criteria.add(Expression.eq("login",login));
		return findSingle(criteria);
	}

	/**
	 * �������� �� ���������� � ������� initiatorLoginId ��������� employeeId
	 * @param employeeId ������������� ����������
	 * @param initiatorLoginId ������������� ������ �����������
	 * @return ������� �����������
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public boolean isAllowed(final Long employeeId, final Long initiatorLoginId) throws AdminException, AdminLogicException
	{
		return execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(CHECK_ALLOWED_QUERY_NAME);
				query.setParameter("employeeId", employeeId);
				query.setParameter("initiatorLoginId", initiatorLoginId);
				return (Boolean) query.uniqueResult();
			}
		});
	}

	/**
	 * @param department �����������
	 * @return ���������� ������� � �������������
	 * @throws AdminException
	 */
	public int getNumberAdmins(final Department department) throws AdminException, AdminLogicException
	{
		return execute(new HibernateAction<Integer>()
		{
			public Integer run(Session session) throws Exception
			{
				Query query = session.getNamedQuery(GET_NUMBER_ADMINS_QUERY_NAME);
				query.setParameter("departmentTB",  department.getTb());
				query.setParameter("departmentOSB", StringUtils.defaultString(department.getOsb(), DEFAULT));
				query.setParameter("departmentVSP", StringUtils.defaultString(department.getVsp(), DEFAULT));
				query.setParameter("toDate", Calendar.getInstance());
				return Integer.decode(query.uniqueResult().toString());
			}
		});
	}

	/**
	 * @param managerId ������������� ���������
	 * @return ���������� � ���������
	 */
	public ManagerInfo getManagerInfo(String managerId) throws AdminException
	{
		/*
			������� ������: AK_MANAGER_ID_UNIQUE
			��������� �������: access("THIS_"."MANAGER_ID"=:P)
			��������������: 1
		*/
		DetachedCriteria criteria = DetachedCriteria.forClass(ManagerInfo.class);
		criteria.add(Expression.eq("id", managerId));
		return findSingle(criteria);
	}
}
