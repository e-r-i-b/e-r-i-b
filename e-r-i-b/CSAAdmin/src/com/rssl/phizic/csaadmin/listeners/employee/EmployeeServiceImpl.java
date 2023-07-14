package com.rssl.phizic.csaadmin.listeners.employee;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.csaadmin.business.access.AccessSchemeService;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartmentService;
import com.rssl.phizic.csaadmin.business.departments.Department;
import com.rssl.phizic.csaadmin.business.employee.*;
import com.rssl.phizic.csaadmin.business.login.*;
import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.csaadmin.listeners.GateMessageHelper;
import com.rssl.phizic.csaadmin.listeners.generated.*;
import com.rssl.phizic.csaadmin.operation.AssignAccessSchemeOperation;
import com.rssl.phizic.csaadmin.operation.EditAllowedDepartmentOperation;
import com.rssl.phizic.csaadmin.operation.EditEmployeeOperation;
import com.rssl.phizic.csaadmin.operation.RemoveEmployeeOperation;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class EmployeeServiceImpl
{
	private static final EmployeeService employeeService = new EmployeeService();
	private static final LoginService loginService = new LoginService();
	private static final LoginBlockService loginBlockService = new LoginBlockService();
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();
	private static final AllowedDepartmentService allowedDepartmentService = new AllowedDepartmentService();

	protected static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	protected static String getStringDateTime(Calendar date)
	{
		return date==null ? null : XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	private Login getLoginByName(String userId) throws AdminException
	{
		return loginService.findByName(userId);
	}

	private EmployeeListFilterParameters toBusiness(EmployeeListFilterParametersType source) throws AdminException
	{
		EmployeeListFilterParameters parameters = new EmployeeListFilterParameters();
		parameters.setFirstResult(source.getFirstResult());
		parameters.setMaxResults(source.getMaxResults());
		parameters.setSeekerLoginId(getLoginByName(source.getSeekerLogin()).getId());
		parameters.setSeekerAllDepartments(source.isSeekerAllDepartments());
		parameters.setSoughtId(source.getSoughtId());
		parameters.setSoughtLogin(source.getSoughtLogin());
		parameters.setSoughtFIO(source.getSoughtFIO());
		parameters.setSoughtBlockedState(source.getSoughtBlockedState());
		parameters.setSoughtBlockedUntil(parseCalendar(source.getSoughtBlockedUntil()));
		parameters.setSoughtInfo(source.getSoughtInfo());
		parameters.setSoughtTB(source.getSoughtTB());
		parameters.setSoughtBranchCode(source.getSoughtBranchCode());
		parameters.setSoughtDepartmentCode(source.getSoughtDepartmentCode());
		return parameters;
	}

	private ContactCenterEmployeeFilterParameters toBusiness(EmployeeMailManagerFilterParametersType source) throws AdminException
	{
		ContactCenterEmployeeFilterParameters parameters = new ContactCenterEmployeeFilterParameters();
		parameters.setFirstResult(source.getFirstResult());
		parameters.setMaxResults(source.getMaxResults());
		parameters.setSoughtBlockedUntil(parseCalendar(source.getSoughtBlockedUntil()));
		parameters.setSoughtId(source.getSoughtId());
		parameters.setSoughtFIO(source.getSoughtFIO());
		parameters.setSoughtArea(source.getSoughtArea());
		return parameters;
	}

	private LoginBlock toBusiness(LoginBlockType source)
	{
		LoginBlock block = new LoginBlock();
		block.setReasonType(BlockType.valueOf(source.getReasonType()));
		block.setReasonDescription(source.getReasonDescription());
		block.setBlockedFrom(parseCalendar(source.getBlockedFrom()));
		block.setBlockedUntil(parseCalendar(source.getBlockedUntil()));
		return block;
	}

	private List<Department> toBusiness(DepartmentType[] source)
	{
		ArrayList<Department> departments = new ArrayList<Department>();
		for (DepartmentType department : source)
			departments.add(new Department(department.getTb(), department.getOsb(), department.getVsp()));
		return departments;
	}

	private void update(Login login, LoginType sourceLogin) throws AdminException
	{
		login.setName(sourceLogin.getName());
		if (StringHelper.isEmpty(login.getPassword()) && StringHelper.isNotEmpty(sourceLogin.getPassword()))
		{
			login.setNewPassword(sourceLogin.getPassword());
			login.setPasswordExpireDate(Calendar.getInstance());
		}

		AccessSchemeType sourceAccessScheme = sourceLogin.getAccessScheme();
		if (sourceAccessScheme != null)
			login.setAccessScheme(accessSchemeService.findById(sourceAccessScheme.getExternalId()));
	}

	private void update(Department department, DepartmentType sourceDepartment)
	{
		department.setTb(sourceDepartment.getTb());
		department.setOsb(sourceDepartment.getOsb());
		department.setVsp(sourceDepartment.getVsp());
	}

	private void update(Employee employee, EmployeeType source) throws AdminException
	{
		employee.setSurname(source.getSurname());
		employee.setFirstName(source.getFirstName());
		employee.setPatronymic(source.getPatronymic());
		employee.setInfo(source.getInfo());
		employee.setEmail(source.getEmail());
		employee.setMobilePhone(source.getMobilePhone());
		employee.setCAAdmin(source.isCaAdmin());
		employee.setVSPEmployee(source.isVspEmployee());
		employee.setManagerId(source.getManagerId());
		employee.setManagerPhone(source.getManagerPhone());
		employee.setManagerEMail(source.getManagerEMail());
		employee.setManagerLeadEMail(source.getManagerLeadEMail());
		employee.setManagerChannel(source.getManagerChannel());
		employee.setSudirLogin(source.getSudirLogin());

		update(employee.getLogin(), source.getLogin());
		if (source.getDepartment() != null)
			update(employee.getDepartment(), source.getDepartment());
	}

	private LoginBlockType toGate(LoginBlock block) throws AdminException
	{
		if (block == null)
			return null;
		LoginBlockType blockType = new LoginBlockType();
		blockType.setReasonType(block.getReasonType().name());
		blockType.setReasonDescription(block.getReasonDescription());
		blockType.setBlockedFrom(getStringDateTime(block.getBlockedFrom()));
		blockType.setBlockedUntil(getStringDateTime(block.getBlockedUntil()));
		blockType.setEmployee(toGate(block.getEmployee(), false));
		return blockType;
	}

	private LoginBlockType[] toGate(List<LoginBlock> blocks) throws AdminException
	{
		if (CollectionUtils.isEmpty(blocks))
			return new LoginBlockType[0];
		LoginBlockType[] loginBlockTypes = new LoginBlockType[blocks.size()];
		int i = 0;
		for (LoginBlock block : blocks)
			loginBlockTypes[i++] = toGate(block);
		return loginBlockTypes;
	}

	private AccessSchemeType toGate(AccessScheme scheme)
	{
		return GateMessageHelper.toGate(scheme);
	}

	private LoginType toGate(Login destination, boolean addLockInfo) throws AdminException
	{
		if (destination==null)
			return null;
		LoginType login = new LoginType();
		login.setId(destination.getId());
		login.setName(destination.getName());
		login.setPassword(destination.getPassword());
		login.setAccessScheme(toGate(destination.getAccessScheme()));
		if (addLockInfo)
		{
			long timeToBlock = ConfigFactory.getConfig(SecurityConfig.class).getTimeToBlock();
			List<LoginBlock> loginBlocks = loginBlockService.getAllForLogin(destination, Calendar.getInstance());
			Calendar today = Calendar.getInstance();
			Calendar lastLogonDate = destination.getLastLogonDate() != null ? destination.getLastLogonDate() : destination.getLastUpdateDate();

			if(timeToBlock > 0 && lastLogonDate != null)
			{
				if(DateHelper.diff(today, lastLogonDate) > timeToBlock)
				{
					LoginBlock longInactivityBlock = new LoginBlock();
					longInactivityBlock.setLogin(destination);
					longInactivityBlock.setBlockedFrom(today);
					longInactivityBlock.setReasonType(BlockType.longInactivity);
					longInactivityBlock.setReasonDescription(Constants.BLOCK_BY_INACTIVITY_REASON_DESCR);
					longInactivityBlock.setEmployee(null);
					loginBlocks.add(longInactivityBlock);
				}
			}
			login.setBlocks(toGate(loginBlocks));
		}
		login.setLastUpdateDate(getStringDateTime(destination.getLastUpdateDate()));
		return login;
	}

	private DepartmentType toGate(Department department)
	{
		if (department == null)
			return null;
		return new DepartmentType(department.getTb(), department.getOsb(), department.getVsp());
	}

	private DepartmentType[] toGate(List<Department> departments)
	{
		DepartmentType[] result = new DepartmentType[departments.size()];
		int i = 0;
		for (Department department : departments)
			result[i++] = toGate(department);

		return result;
	}

	private ManagerInfoType toGate(ManagerInfo managerInfo)
	{
		if (managerInfo == null)
			return null;

		ManagerInfoType result = new ManagerInfoType();
		result.setName(managerInfo.getName());
		result.setEmail(managerInfo.getEmail());
		result.setPhone(managerInfo.getPhone());
		return result;
	}

	private EmployeeType toGate(Employee destination, boolean addLockInfo) throws AdminException
	{
		EmployeeType employee = new EmployeeType();
		employee.setExternalId(destination.getExternalId());
		employee.setLogin(toGate(destination.getLogin(), addLockInfo));
		employee.setSurname(destination.getSurname());
		employee.setFirstName(destination.getFirstName());
		employee.setPatronymic(destination.getPatronymic());
		employee.setInfo(destination.getInfo());
		employee.setEmail(destination.getEmail());
		employee.setMobilePhone(destination.getMobilePhone());
		employee.setDepartment(toGate(destination.getDepartment()));
		employee.setCaAdmin(destination.isCAAdmin());
		employee.setVspEmployee(destination.isVSPEmployee());
		employee.setManagerId(destination.getManagerId());
		employee.setManagerPhone(destination.getManagerPhone());
		employee.setManagerEMail(destination.getManagerEMail());
		employee.setManagerLeadEMail(destination.getManagerLeadEMail());
		employee.setManagerChannel(destination.getManagerChannel());
		employee.setSudirLogin(destination.getSudirLogin());
		return employee;
	}

	private ContactCenterEmployeeType toGate(ContactCenterEmployee destination) throws AdminException
	{
		ContactCenterEmployeeType employee = new ContactCenterEmployeeType();
		employee.setId(destination.getId());
		employee.setExternalId(destination.getExternalId());
		employee.setName(destination.getName());
		employee.setDepartment(destination.getDepartment());
		employee.setArea(destination.getArea());
		return employee;
	}

	/**
	 * @param parametersType параметры поиска
	 * @return список сотрудников
	 * @throws AdminException
	 */
	public EmployeeType[] getList(EmployeeListFilterParametersType parametersType) throws AdminException, AdminLogicException
	{
		EmployeeListFilterParameters parameters = toBusiness(parametersType);
		List<Employee> employeeList = employeeService.getList(parameters);
		if (CollectionUtils.isEmpty(employeeList))
			return new EmployeeType[0];

		EmployeeType[] gateEmployees = new EmployeeType[employeeList.size()];
		int i = 0;
		for (Employee employee : employeeList)
			gateEmployees[i++] = toGate(employee, true);
		return gateEmployees;
	}

	/**
	 * @param parameters параметры поиска
	 * @return список сотрудников контактного центра
	 * @throws AdminLogicException
	 * @throws AdminException
	 */
	public ContactCenterEmployeeType[] getMailManagerList(EmployeeMailManagerFilterParametersType parameters)  throws AdminException, AdminLogicException
	{

		List<ContactCenterEmployee> employeeList = employeeService.getMailManagerList(toBusiness(parameters));
		if (CollectionUtils.isEmpty(employeeList))
			return new ContactCenterEmployeeType[0];

		ContactCenterEmployeeType[] gateEmployees = new ContactCenterEmployeeType[employeeList.size()];
		int i = 0;
		for (ContactCenterEmployee employee : employeeList)
			gateEmployees[i++] = toGate(employee);
		return gateEmployees;
	}

	/**
	 * @param id идентификатор
	 * @param initiatorLogin логин сотрудника инициирующего действие
	 * @return сотрудник с идентификаотром id
	 * @throws AdminException
	 */
	public EmployeeType getById(Long id, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		EditEmployeeOperation operation = new EditEmployeeOperation();
		operation.setInitiator(initiatorLogin);
		operation.initialize(id);
		return toGate(operation.getEmployee(), true);
	}

	/**
	 * @param login логин сотрудника
	 * @return сотрудник с идентификаотром id
	 * @throws AdminException
	 */
	public EmployeeType getByLogin(Login login) throws AdminException
	{
		return toGate(employeeService.findByLogin(login), true);
	}

	/**
	 * получить данные для синхронизации по логину сотрудника
	 * @param loginId логин сотрудника
	 * @return данные для синхронизации
	 */
	public GetEmployeeSynchronizationDataResultType getGetEmployeeSynchronizationData(String loginId) throws AdminException, AdminLogicException
	{
		GetEmployeeSynchronizationDataResultType data = new GetEmployeeSynchronizationDataResultType();
		Login employeeLogin = loginService.findByName(loginId);
		Employee employee = employeeService.findByLogin(employeeLogin);
		if (employee != null)
			data.setEmployee(toGate(employee, false));
		data.setLogin(toGate(employeeLogin, false));
		data.setAllowedDepartments(toGate(allowedDepartmentService.getAllowedByLogin(employeeLogin)));
		return data;
	}

	/**
	 * сохранение данных сотрудника
	 * @param changeableEmployee резактируемый сотрудник
	 * @param initiatorLogin редактирующий сотрудник
	 * @return сохраненный сотрудник
	 * @throws AdminException
	 */
	public EmployeeType save(EmployeeType changeableEmployee, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		EditEmployeeOperation operation = new EditEmployeeOperation();
		operation.setInitiator(initiatorLogin);
		Long externalId = changeableEmployee.getExternalId();
		if (externalId == null)
			operation.initialize();
		else
			operation.initialize(externalId);

		update(operation.getEmployee(), changeableEmployee);
		operation.execute();
		return toGate(operation.getEmployee(), false);
	}

	/**
	 * сохранение данных сотрудника
	 * @param schemeId идентификатор схемы
	 * @param changeableEmployee резактируемый сотрудник
	 * @param initiatorLogin редактирующий сотрудник
	 * @return сохраненный сотрудник
	 * @throws AdminException
	 */
	public AccessSchemeType assignAccessScheme(Long schemeId, EmployeeType changeableEmployee, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		AssignAccessSchemeOperation operation = new AssignAccessSchemeOperation();
		operation.setInitiator(initiatorLogin);
		operation.initialize(changeableEmployee.getExternalId(), schemeId);
		operation.execute();
		return toGate(operation.getScheme());
	}

	/**
	 * удалить данные о сотруднике
	 * @param employee сотрудник
	 * @param initiatorLogin логин блокирующего сотрудника
	 * @throws AdminException
	 */
	public void delete(EmployeeType employee, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		RemoveEmployeeOperation operation = new RemoveEmployeeOperation();
		operation.setInitiator(initiatorLogin);
		operation.initialize(employee.getExternalId());
		operation.execute();
	}

	/**
	 * заблокировать логин сотрудника
	 * @param lockedLogin логин блокируемого сотрудника
	 * @param block блокировка
	 * @param initiatorLogin логин блокирующего сотрудника
	 * @throws AdminException
	 */
	public void lock(LoginType lockedLogin, LoginBlockType block, Login initiatorLogin) throws AdminException
	{
		Login locked = getLoginByName(lockedLogin.getName());
		LoginBlock loginBlock = toBusiness(block);
		loginBlock.setLogin(locked);
		loginBlock.setEmployee(initiatorLogin);
		loginBlockService.save(loginBlock);
	}

	/**
	 * разблокировать логин сотрудника
	 * @param login логин сотрудника
	 * @return список снятых блокировок
	 * @throws AdminException
	 */
	public LoginBlockType[] unlock(LoginType login) throws AdminException, AdminLogicException
	{
		Login loginForUnlock = getLoginByName(login.getName());
		Calendar today = Calendar.getInstance();
		List<LoginBlock> blocks = loginBlockService.unlock(loginForUnlock, today).getFirst();
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		Calendar lastLogonDate = loginForUnlock.getLastLogonDate() != null ? loginForUnlock.getLastLogonDate() : loginForUnlock.getLastUpdateDate();
		if(lastLogonDate != null)
		{
			if(securityConfig.getTimeToBlock() > 0 && DateHelper.diff(today, lastLogonDate) > securityConfig.getTimeToBlock())
			{
				loginForUnlock.setLastLogonDate(today);
				loginService.update(loginForUnlock);
			}
		}
		return toGate(blocks);
	}

	/**
	 * изменить пароль сотрудника
	 * @param login логин сотрудника
	 * @return измененный пароль
	 * @throws AdminException
	 */
	public LoginType changePassword(LoginType login) throws AdminException
	{
		Login changingLogin = getLoginByName(login.getName());
		changingLogin.setNewPassword(login.getPassword());
		changingLogin.setPasswordExpireDate(Calendar.getInstance());
		return toGate(loginService.update(changingLogin), false);
	}

	public void selfChangePassword(Session session, String password) throws AdminException, AdminLogicException
	{
		CryptoService cryptoService = SecurityFactory.cryptoService();
		Login currentLogin = session.getLogin();
		if(currentLogin.getPassword().equals(cryptoService.hash(password)))
			throw new AdminLogicException("Введенный пароль совпадает с предыдущим");

		currentLogin.setNewPassword(password);
		currentLogin.setPasswordExpireDate(calculateExpireDate());

		loginService.update(currentLogin);
	}

	private Calendar calculateExpireDate()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		Calendar expireDate = DateHelper.getCurrentDate();
		expireDate.add(Calendar.DATE, securityConfig.getPasswordLifeTime() + 1);
		return expireDate;
	}

	/**
	 * поиск доступных подразделений
	 * @param employee сотрудник для которого ищем подразделения
	 * @param initiatorLogin логин блокирующего сотрудника
	 * @return доступные подразделения для
	 * @throws AdminException
	 */
	public DepartmentType[] getAllowedDepartments(EmployeeType employee, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		EditAllowedDepartmentOperation operation = new EditAllowedDepartmentOperation();
		operation.setInitiator(initiatorLogin);
		operation.initialize(employee.getLogin().getName());
		return toGate(operation.getAllowedDepartment());
	}

	/**
	 * сохраниение доступных подразделений
	 * @param addDepartments новые департаменты
	 * @param deleteDepartments удаляемые департаменты
	 * @param employee сотрудник для которого ищем подразделения
	 * @param initiatorLogin логин блокирующего сотрудника
	 * @return доступные подразделения для
	 * @throws AdminException
	 */
	public DepartmentType[] saveAllowedDepartments(DepartmentType[] addDepartments, DepartmentType[] deleteDepartments, EmployeeType employee, Login initiatorLogin) throws AdminException, AdminLogicException
	{
		EditAllowedDepartmentOperation operation = new EditAllowedDepartmentOperation();
		operation.setInitiator(initiatorLogin);
		operation.initialize(employee.getLogin().getName());
		operation.setAddDepartments(toBusiness(addDepartments));
		operation.setDeleteDepartments(toBusiness(deleteDepartments));
		operation.execute();
		return toGate(operation.getAllowedDepartment());
	}

	/**
	 * @param managerId идентификатор менеджера
	 * @return информация о менеджере
	 */
	public ManagerInfoType getManagerInfo(String managerId) throws AdminException
	{
		return toGate(employeeService.getManagerInfo(managerId));
	}
}
