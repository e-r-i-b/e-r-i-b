package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartment;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartmentService;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.employee.Employee;
import com.rssl.phizic.csaadmin.business.employee.EmployeeService;
import com.rssl.phizic.csaadmin.business.login.LoginService;
import com.rssl.phizic.csaadmin.config.CSAAdminConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


/**
 * @author akrenev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Ѕазова€ операци€ работы с сотрудниками
 */

public abstract class EmployeeOperationBase extends OperationBase
{
	protected static final String MANAGER_ID_UNIQUE = "MANAGER_ID_UNIQUE";
	protected static final EmployeeService employeeService = new EmployeeService();
	protected static final LoginService loginService = new LoginService();

	private static final AllowedDepartmentService allowedDepartmentService = new AllowedDepartmentService();
	private static final String ADMIN_CATEGORY = "A";

	private Employee employee;
	private AccessScheme oldScheme;
	private Department oldDepartment;

	protected void initialize(Employee employee) throws AdminException, AdminLogicException
	{
		this.employee = employee;
		if (employee == null)
			throw new AdminLogicException("—отрудник не найден.");

		Long employeeId = employee.getExternalId();
		if (employeeId != null && !employeeService.isAllowed(employeeId, getInitiator().getId()))
			throw new AdminLogicException("” сотрудника " + getInitiator().getName() + " нет доступа к сотруднику " + employee.getLogin().getName() + ".");

		oldScheme = employee.getLogin().getAccessScheme();
		if (employee.getDepartment() != null)
			oldDepartment = new Department(employee.getDepartment());
	}

	/**
	 * инициализаци€ операции существующим сотрудником
	 * @param externalId редактируемый сотрудник
	 * @throws AdminException
	 */
	public void initialize(Long externalId) throws AdminException, AdminLogicException
	{
		initialize(employeeService.findById(externalId));
	}

	protected boolean isDepartmentChanged()
	{
		Department newDepartment = employee.getDepartment();
		if (oldDepartment == null && newDepartment == null)
			return false;

		if (oldDepartment == null || newDepartment == null)
			return true;

		return  !StringUtils.equals(oldDepartment.getTb(),  newDepartment.getTb())  ||
				!StringUtils.equals(oldDepartment.getOsb(), newDepartment.getOsb()) ||
				!StringUtils.equals(oldDepartment.getVsp(), newDepartment.getVsp());
	}
	
	protected void checkAdminCount() throws AdminLogicException, AdminException
	{
		AccessScheme newScheme = employee.getLogin().getAccessScheme();
		if (newScheme == null || !ADMIN_CATEGORY.equals(newScheme.getCategory()))
			return;

		if (oldScheme != null && ADMIN_CATEGORY.equals(oldScheme.getCategory()) && !isDepartmentChanged())
			return;

		int numberDepartmentAdmins = employeeService.getNumberAdmins(employee.getDepartment());
		CSAAdminConfig config = ConfigFactory.getConfig(CSAAdminConfig.class);
		if(numberDepartmentAdmins + 1 > config.getDepartmentAdminsLimit())
			throw new AdminLogicException("ѕревышен лимит подключений администраторов.");
	}

	protected void updateAllowedDepartments() throws AdminException, AdminLogicException
	{
		if (oldDepartment != null)
			allowedDepartmentService.deleteAllAllowed(employee.getLogin());

		AccessScheme newScheme = employee.getLogin().getAccessScheme();
		if (newScheme == null)
			return;

		if (ADMIN_CATEGORY.equals(newScheme.getCategory()))
			allowedDepartmentService.merge(employee.getLogin(), getInitiator());
		else
			allowedDepartmentService.save(new AllowedDepartment(employee.getLogin(), employee.getDepartment()));
	}

	/**
	 * @return редактируемый сотрудник
	 */
	public Employee getEmployee()
	{
		return employee;
	}

	protected void updateLogin() throws AdminException
	{
		loginService.update(employee.getLogin());
	}

	@Override
	public final void execute() throws AdminException, AdminLogicException
	{
		try
		{
			executeAtomic(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					process();
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new AdminLogicException(getConstraintViolationExceptionMessage(e.getCause().getMessage()), e);
		}
	}

	protected abstract void process() throws Exception;

	protected abstract String getConstraintViolationExceptionMessage(String constraintMessage);
}
