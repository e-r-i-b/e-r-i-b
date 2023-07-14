package com.rssl.phizicgate.manager.services.routable.employee;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.employee.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.gate.schemes.AccessScheme;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ћаршрутизарующий сервис работы с сотрудниками
 */

public class EmployeeServiceImpl extends AbstractService implements EmployeeService
{
	private final Object serviceCreatorLocker = new Object();

	private volatile EmployeeService businessDelegate;
	private volatile EmployeeService gateDelegate;

	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которой создаетс€ сервис
	 */
	public EmployeeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	private EmployeeService getBusinessDelegate()
	{
		if (businessDelegate == null)
		{
			synchronized (serviceCreatorLocker)
			{
				if (businessDelegate == null)
					businessDelegate = (EmployeeService) getDelegate(EmployeeService.class.getName() + BUSINESS_DELEGATE_KEY);
			}
		}
		return businessDelegate;
	}

	private EmployeeService getGateDelegate()
	{
		if (gateDelegate == null)
		{
			synchronized (serviceCreatorLocker)
			{
				if (gateDelegate == null)
					gateDelegate = (EmployeeService) getDelegate(EmployeeService.class.getName() + GATE_DELEGATE_KEY);
			}
		}
		return gateDelegate;
	}

	private boolean isMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}

	private EmployeeService getDelegate()
	{
		if (isMultiBlockMode())
			return getGateDelegate();
		return getBusinessDelegate();
	}

	public List<Employee> getList(EmployeeListFilterParameters filterParameters) throws GateException, GateLogicException
	{
		return getDelegate().getList(filterParameters);
	}

	public List<ContactCenterEmployee> getListMailManagers(ContactCenterEmployeeFilterParameters filterParameters) throws GateException, GateLogicException
	{
		return getGateDelegate().getListMailManagers(filterParameters);
	}

	public ManagerInfo getManagerInfo(String managerId) throws GateException, GateLogicException
	{
		return getDelegate().getManagerInfo(managerId);
	}

	public Employee getCurrent() throws GateException, GateLogicException
	{
		return getDelegate().getCurrent();
	}

	public Employee getById(Long id) throws GateException, GateLogicException
	{
		return getDelegate().getById(id);
	}

	public Employee save(Employee employee) throws GateException, GateLogicException
	{
		return getDelegate().save(employee);
	}

	public AccessScheme assign(Long schemeId, Employee employee) throws GateException, GateLogicException
	{
		return getDelegate().assign(schemeId, employee);
	}

	public AccessScheme assign(List<Long> serviceIds, String category, Employee employee) throws GateException, GateLogicException
	{
		return getDelegate().assign(serviceIds, category, employee);
	}

	public void delete(Employee employee) throws GateException, GateLogicException
	{
		getDelegate().delete(employee);
	}

	public void lock(Login lockedLogin, LoginBlock block) throws GateException, GateLogicException
	{
		getDelegate().lock(lockedLogin, block);
	}

	public List<LoginBlock> unlock(Login login) throws GateException, GateLogicException
	{
		return getDelegate().unlock(login);
	}

	public Login changePassword(Login login) throws GateException, GateLogicException
	{
		return getDelegate().changePassword(login);
	}

	public void selfPasswordChange(String password) throws GateException, GateLogicException
	{
		getDelegate().selfPasswordChange(password);
	}

	public List<AllowedDepartment> getAllowedDepartments(Employee employee) throws GateException, GateLogicException
	{
		return getDelegate().getAllowedDepartments(employee);
	}

	public void saveAllowedDepartments(Employee employee, List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws GateException, GateLogicException
	{
		getDelegate().saveAllowedDepartments(employee, addAllowedDepartments, removeAllowedDepartments);
	}
}
