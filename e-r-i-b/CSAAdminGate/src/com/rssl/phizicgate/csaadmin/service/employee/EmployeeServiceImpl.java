package com.rssl.phizicgate.csaadmin.service.employee;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.employee.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizicgate.csaadmin.service.CSAAdminServiceBase;
import com.rssl.phizicgate.csaadmin.service.generated.*;
import com.rssl.phizicgate.csaadmin.service.types.*;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * гейтовый сервис работы с сотрудниками
 */

public class EmployeeServiceImpl extends CSAAdminServiceBase implements EmployeeService
{
	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которой создаетс€ сервис
	 */
	public EmployeeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Employee> getList(EmployeeListFilterParameters filterParameters) throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetEmployeeListRq(new GetEmployeeListParametersType(toGate(filterParameters)));
			ResponseData response = process(data);
			EmployeeType[] result = response.getGetEmployeeListRs().getEmployees();
			if (ArrayUtils.isEmpty(result))
				return Collections.emptyList();

			List<Employee> employeeList = new ArrayList<Employee>(result.length);
			for (EmployeeType employeeType : result)
				employeeList.add(new EmployeeImpl(employeeType));
			return employeeList;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<ContactCenterEmployee> getListMailManagers(ContactCenterEmployeeFilterParameters filterParameters) throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetEmployeeMailManagerListRq(new GetEmployeeMailManagerListParametersType(toGate(filterParameters)));
			ResponseData response = process(data);
			ContactCenterEmployeeType[] result = response.getGetEmployeeMailManagerListRs().getEmployees();
			if (ArrayUtils.isEmpty(result))
				return Collections.emptyList();

			List<ContactCenterEmployee> employeeList = new ArrayList<ContactCenterEmployee>(result.length);
			for (ContactCenterEmployeeType employee : result)
				employeeList.add(new ContactCenterEmployeeImpl(employee));
			return employeeList;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public ManagerInfo getManagerInfo(String managerId) throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetEmployeeManagerInfoRq(new GetEmployeeManagerInfoParametersType(managerId));
			ResponseData response = process(data);
			ManagerInfoType managerInfo = response.getGetEmployeeManagerInfoRs().getManagerInfo();
			if (managerInfo == null)
				return null;

			return new ManagerInfoImpl(managerInfo);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Employee getCurrent() throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetCurrentEmployeeRq(new GetCurrentEmployeeParametersType());
			ResponseData response = process(data);
			return new EmployeeImpl(response.getGetCurrentEmployeeRs().getEmployee());
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Employee getById(Long id) throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetEmployeeByIdRq(new GetEmployeeByIdParametersType(id));
			ResponseData response = process(data);
			return new EmployeeImpl(response.getGetEmployeeByIdRs().getEmployee());
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Employee save(Employee changeableEmployee) throws GateException, GateLogicException
	{
		try
		{
			EmployeeType changeable = toGate(changeableEmployee);
			RequestData data = new RequestData();
			data.setSaveEmployeeRq(new SaveEmployeeParametersType(changeable));
			ResponseData response = process(data);
			return new EmployeeImpl(response.getSaveEmployeeRs().getEmployee());
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public AccessScheme assign(Long schemeId, Employee changeableEmployee) throws GateException, GateLogicException
	{
		try
		{
			EmployeeType changeable = toGate(changeableEmployee);
			RequestData data = new RequestData();
			data.setAssignAccessSchemeEmployeeRq(new AssignAccessSchemeEmployeeParametersType(schemeId, changeable));
			ResponseData response = process(data);
			AccessSchemeType accessScheme = response.getAssignAccessSchemeEmployeeRs().getAccessScheme();
			if (accessScheme == null)
				return null;
			return new AccessSchemeImpl(accessScheme);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public AccessScheme assign(List<Long> serviceIds, String category, Employee changeableEmployee)
	{
		throw new UnsupportedOperationException("Ќазначение индивидуальной схемы прав запрещено");
	}

	public void delete(Employee employee) throws GateException, GateLogicException
	{
		try
		{
			EmployeeType parameter = toGate(employee);
			RequestData data = new RequestData();
			data.setDeleteEmployeeRq(new DeleteEmployeeParametersType(parameter));
			process(data);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void lock(Login lockedLogin, LoginBlock block) throws GateException, GateLogicException
	{
		try
		{
			LoginType lockedLoginParameter = new LoginType();
			lockedLoginParameter.setName(lockedLogin.getUserId());
			RequestData data = new RequestData();
			data.setLockEmployeeRq(new LockEmployeeParametersType(lockedLoginParameter, toGate(block)));
			process(data);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<LoginBlock> unlock(Login login) throws GateException, GateLogicException
	{
		try
		{
			LoginType parameter = new LoginType();
			parameter.setName(login.getUserId());
			RequestData data = new RequestData();
			data.setUnlockEmployeeRq(new UnlockEmployeeParametersType(parameter));
			ResponseData response = process(data);
			LoginBlockType[] unlock = response.getUnlockEmployeeRs().getBlocks();
			if (ArrayUtils.isEmpty(unlock))
				return Collections.emptyList();
			List<LoginBlock> loginBlocks = new ArrayList<LoginBlock>(unlock.length);
			for (LoginBlockType block : unlock)
				loginBlocks.add(fromGate(block));
			return loginBlocks;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Login changePassword(Login login) throws GateException, GateLogicException
	{
		try
		{
			LoginType parameter = new LoginType();
			parameter.setName(login.getUserId());
			parameter.setPassword(login.getPassword());
			RequestData data = new RequestData();
			data.setChangeEmployeePasswordRq(new ChangeEmployeePasswordParametersType(parameter));
			ResponseData response = process(data);
			LoginType result = response.getChangeEmployeePasswordRs().getLogin();
			return new LoginImpl(result);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void selfPasswordChange(String password) throws GateException, GateLogicException
	{
		RequestData data = new RequestData();
		data.setSelfChangePasswordRq(new SelfChangePasswordParametersType(password));
		process(data);
	}

	public List<AllowedDepartment> getAllowedDepartments(Employee employee) throws GateException, GateLogicException
	{
		try
		{
			RequestData data = new RequestData();
			data.setGetAllowedDepartmentsRq(new GetAllowedDepartmentsParametersType(toGate(employee)));
			ResponseData response = process(data);
			DepartmentType[] newAllowedDepartments = response.getGetAllowedDepartmentsRs().getAllowedDepartments();
			List<AllowedDepartment> allowedDepartments = new ArrayList<AllowedDepartment>();
			for (DepartmentType department : newAllowedDepartments)
				allowedDepartments.add(new AllowedDepartment(department.getTb(), department.getOsb(), department.getVsp()));
			return allowedDepartments;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void saveAllowedDepartments(Employee employee, List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws GateException, GateLogicException
	{
		try
		{
			DepartmentType[] addDepartmentsGateInstance = toGate(addAllowedDepartments);
			DepartmentType[] deleteDepartmentsGateInstance = toGate(removeAllowedDepartments);

			RequestData data = new RequestData();
			data.setSaveAllowedDepartmentsRq(new SaveAllowedDepartmentsParametersType(addDepartmentsGateInstance, deleteDepartmentsGateInstance, toGate(employee)));
			process(data);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
