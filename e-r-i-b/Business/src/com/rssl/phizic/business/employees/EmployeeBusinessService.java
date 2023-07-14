package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.PersonalAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.gate.employee.ContactCenterEmployee;
import com.rssl.phizic.gate.employee.ContactCenterEmployeeFilterParameters;
import com.rssl.phizic.gate.employee.ManagerInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.login.Login;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.ManualPasswordGenerator;
import com.rssl.phizic.security.password.UserBlocksValidator;
import com.rssl.phizic.security.password.UserPasswordChanger;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author akrenev
 * @ created 10.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Бизнесовая реализация гейтового сервиса работы с сотрудниками
 */

public class EmployeeBusinessService extends AbstractService implements com.rssl.phizic.gate.employee.EmployeeService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String LIST_QUERY_NAME = "com.rssl.phizic.business.employees.queries.list";
	private static final String CHECK_TB_QUERY_NAME = "com.rssl.phizic.business.employees.queries.checkTb";
	private static final String CHECK_OSB_QUERY_NAME = "com.rssl.phizic.business.employees.queries.checkOsb";
	private static final String CHECK_VSP_QUERY_NAME = "com.rssl.phizic.business.employees.queries.checkVsp";
	private static final AccessType ACCESS_TYPE = AccessType.employee;

	private static final SimpleService service = new SimpleService();
	private static final EmployeeService employeeService  = new EmployeeService();
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();
	private final static AccessPolicyService accessModesService  = new AccessPolicyService();
	private static final SecurityService securityService = new SecurityService();
	private static final ChannelService channelService = new ChannelService();
	private static final DepartmentService departmentService = new DepartmentService();


	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которой создается сервис
	 */
	public EmployeeBusinessService(GateFactory factory)
	{
		super(factory);
	}

	private Employee getInitiator()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
	}

	/**
	 * @param parameters параметры поиска сотрудников
	 * @return список сотрудников
	 * @throws GateException
	 */
	public List<com.rssl.phizic.gate.employee.Employee> getList(com.rssl.phizic.gate.employee.EmployeeListFilterParameters parameters) throws GateException
	{
		try
		{
			BeanQuery query = new BeanQuery(parameters, LIST_QUERY_NAME);
			query.setMaxResults(parameters.getMaxResults());
			query.setFirstResult(parameters.getFirstResult());
			return query.executeListInternal();
		}
		catch (DataAccessException e)
		{
			throw new GateException(e);
		}
	}

	public List<ContactCenterEmployee> getListMailManagers(ContactCenterEmployeeFilterParameters filterParameters) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Нужно использовать com.rssl.phizic.operations.employees.GetEmployeeListOperation contactCenterEmployeeList");
	}

	public ManagerInfo getManagerInfo(String managerId) throws GateException, GateLogicException
	{
		try
		{
			/*
				Опорный объект: AK_MANAGER_ID_UNIQUE
				Предикаты доступа: access("THIS_"."MANAGER_ID"=:P)
				Кардинальность: 1
			*/
			DetachedCriteria criteria = DetachedCriteria.forClass(ClientManagerInfo.class);
			criteria.add(Expression.eq("id", managerId));
			return service.findSingle(criteria);
		}
		catch (BusinessException e)
		{
			throw new GateException("Ошибка получения информации о менеджере " + managerId, e);
		}
	}

	public com.rssl.phizic.gate.employee.Employee getCurrent() throws GateException, GateLogicException
	{
		return EmployeeWrapper.getNewInstance(getInitiator());
	}

	public EmployeeWrapper getById(Long id) throws GateException
	{
		try
		{
			return service.findSingle(DetachedCriteria.forClass(EmployeeWrapper.class).add(Expression.eq("employeeId", id)));
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private void createEmployee(EmployeeWrapper employeeWrapper) throws GateException, GateLogicException
	{
		try
		{
			BankLoginGenerator loginGenerator = new BankLoginGenerator(employeeWrapper.getLogin().getUserId(), employeeWrapper.getLogin().getPassword());
			BankLogin bankLogin = loginGenerator.generate();
			Employee employee = employeeWrapper.getEmployee();
			employee.setLogin(bankLogin);
			employeeService.add(employee);
			schemeOwnService.setEmployeeDefaultScheme(bankLogin);
		}
		catch (SecurityDbException e)
		{
			throw new GateException(e);
		}
		catch (SecurityLogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private void checkAdminCount(Employee employee, com.rssl.phizic.gate.schemes.AccessScheme newScheme, com.rssl.phizic.gate.schemes.AccessScheme oldScheme) throws GateLogicException, GateException
	{
		if (newScheme == null || !AccessCategory.CATEGORY_ADMIN.equals(newScheme.getCategory()))
			return;

		if (oldScheme != null && AccessCategory.CATEGORY_ADMIN.equals(oldScheme.getCategory()))
			return;

		try
		{
			int numberDepartmentAdmins = employeeService.getNumberAdmins(employee.getDepartmentId());
			SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			if(numberDepartmentAdmins + 1 > securityConfig.getDepartmentAdminsLimit())
				throw new GateLogicException("Превышен лимит подключений администраторов.");
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * сохранение данных сотрудника
	 *
	 * @param changeableEmployee редактируемый сотрудник
	 * @return сохраненный сотрудник
	 * @throws GateException
	 */
	public com.rssl.phizic.gate.employee.Employee save(final com.rssl.phizic.gate.employee.Employee changeableEmployee) throws GateException, GateLogicException
	{
		final EmployeeWrapper changeableBusinessEmployee = (EmployeeWrapper) changeableEmployee;
		final Employee currentEmployee = getInitiator();

		if (StringHelper.isNotEmpty(changeableEmployee.getManagerId()))
		{
			try
			{
				Employee temp = employeeService.findByManagerId(changeableEmployee.getManagerId(), null);
				if(temp != null && !changeableEmployee.getId().equals(temp.getId()))
					throw new GateLogicException("Такой идентификатор уже существует в системе. Он присвоен сотруднику "+ temp.getFullName()+" с логином "+temp.getLogin().getUserId()+".");
			}
			catch (BusinessException e)
			{
				throw new GateException(e);
			}
		}

		//noinspection OverlyComplexAnonymousInnerClass
		return execute(new HibernateAction<com.rssl.phizic.gate.employee.Employee>()
		{
			public com.rssl.phizic.gate.employee.Employee run(Session session) throws Exception
			{
				if (changeableBusinessEmployee.getId() == null)
				{
					createEmployee(changeableBusinessEmployee);
				}
				else
				{
					Employee employee = changeableBusinessEmployee.getEmployee();
					checkAdminCount(employee, changeableEmployee.getScheme(), null);
					String managerChannel = changeableEmployee.getManagerChannel();
					if (StringHelper.isNotEmpty(managerChannel))
					{
						Channel channel = channelService.getByName(managerChannel);
						Long channelId = channel == null ? null : channel.getId();
						employee.setChannelId(channelId);
					}
					employeeService.update(employee);
				}

				Department newDepartment = changeableBusinessEmployee.getDepartment();
				Department oldDepartment = changeableBusinessEmployee.getOldDepartment();
				//если администратор меняет свое подразделение, то привязки менять не нужно.
				if (currentEmployee.getId() != null && (currentEmployee.getId().equals(changeableBusinessEmployee.getId()) || oldDepartment != null && oldDepartment.getId().equals(newDepartment.getId())))
					return changeableBusinessEmployee;

				Employee employee = changeableBusinessEmployee.getEmployee();
				AccessScheme accessScheme = schemeOwnService.findScheme(employee.getLogin(), AccessType.employee);
				if (accessScheme == null)
					return changeableBusinessEmployee;
				return changeableBusinessEmployee;
			}
		});
	}

	public com.rssl.phizic.gate.schemes.AccessScheme assign(Long schemeId, com.rssl.phizic.gate.employee.Employee changeableEmployee) throws GateException, GateLogicException
	{
		final EmployeeWrapper changeableEmployeeWrapper = (EmployeeWrapper) changeableEmployee;
		final Employee changeableBusinessEmployee = changeableEmployeeWrapper.getEmployee();
		final Employee changingBusinessEmployee = getInitiator();

		final AccessScheme oldScheme = (AccessScheme) changeableEmployeeWrapper.getScheme();
		final AccessScheme newScheme = getSchemeById(schemeId);

		checkAdminCount(changeableBusinessEmployee, newScheme, oldScheme);

		final CommonLogin changeableEmployeeLogin = changeableBusinessEmployee.getLogin();
		final Properties properties = getProperties(changeableEmployeeLogin);

		return execute(new HibernateAction<com.rssl.phizic.gate.schemes.AccessScheme>()
		{
			public com.rssl.phizic.gate.schemes.AccessScheme run(Session session) throws Exception
			{
				try
				{
					accessModesService.enableAccess(changeableEmployeeLogin, ACCESS_TYPE, properties);
				}
				catch (SecurityDbException e)
				{
					throw new GateException(e);
				}

				boolean isModify = false;
				if (newScheme != null)
				{
					schemeOwnService.setScheme(changeableEmployeeLogin, ACCESS_TYPE, newScheme);
					log.info("Установлена новая схема доступа " + newScheme.getName() + " для пользователя login ID " + changeableEmployeeLogin.getId());
					isModify = oldScheme == null || !oldScheme.getCategory().equals(newScheme.getCategory());
				}
				else
				{
					schemeOwnService.removeScheme(changeableEmployeeLogin, ACCESS_TYPE);
					log.info("Удалена схема доступа для пользователя login ID " + changeableEmployeeLogin.getId());
					isModify = oldScheme != null;
				}

				afterAssign(newScheme, oldScheme, changeableBusinessEmployee, changingBusinessEmployee, isModify);
				return newScheme;
			}
		});
	}

	public com.rssl.phizic.gate.schemes.AccessScheme assign(List<Long> serviceIds, final String category, com.rssl.phizic.gate.employee.Employee changeableEmployee) throws GateException, GateLogicException
	{
		final EmployeeWrapper changeableEmployeeWrapper = (EmployeeWrapper) changeableEmployee;
		final Employee changeableBusinessEmployee = changeableEmployeeWrapper.getEmployee();
		final Employee changingBusinessEmployee = getInitiator();

		final AccessScheme oldScheme = (AccessScheme) changeableEmployeeWrapper.getScheme();
		final List<Service> newPersonalServices = getServices(serviceIds, category);
		final AccessScheme newScheme = accessSchemeService.createPersonalScheme(category, newPersonalServices);

		checkAdminCount(changeableBusinessEmployee, newScheme, oldScheme);

		final CommonLogin changeableEmployeeLogin = changeableBusinessEmployee.getLogin();
		final Properties properties = getProperties(changeableEmployeeLogin);

		return execute(new HibernateAction<com.rssl.phizic.gate.schemes.AccessScheme>()
		{
			public com.rssl.phizic.gate.schemes.AccessScheme run(Session session) throws Exception
			{
				try
				{
					accessModesService.enableAccess(changeableEmployeeLogin, ACCESS_TYPE, properties);
				}
				catch (SecurityDbException e)
				{
					throw new GateException(e);
				}

				accessSchemeService.save(newScheme);
				schemeOwnService.setScheme(changeableEmployeeLogin, ACCESS_TYPE, newScheme);
				log.info("Установлен персональный набор прав для пользователя login ID " + changeableEmployeeLogin.getId());

				afterAssign(newScheme, oldScheme, changeableBusinessEmployee, changingBusinessEmployee, oldScheme == null || !category.equals(oldScheme.getCategory()));
				return newScheme;
			}
		});
	}

	private AccessScheme getSchemeById(Long schemeId) throws GateException
	{
		try
		{
			return accessSchemeService.findById(schemeId);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private List<Service> getServices(List<Long> serviceIds, String category) throws GateException
	{
		try
		{
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Service.class);
			detachedCriteria.add(Expression.in("id", serviceIds));
			detachedCriteria.add(Expression.eq("category", category));
			return EmployeeBusinessService.service.find(detachedCriteria);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private Properties getProperties(CommonLogin changeableEmployeeLogin) throws GateException
	{
		try
		{
			return accessModesService.getProperties(changeableEmployeeLogin, ACCESS_TYPE);
		}
		catch (SecurityDbException e)
		{
			throw new GateException(e);
		}
	}

	private void afterAssign(AccessScheme newScheme, AccessScheme oldScheme, Employee changeableBusinessEmployee, Employee changingBusinessEmployee, boolean modify) throws BusinessException, BusinessLogicException
	{
		// удалить старую персональную схему доступа
		if (oldScheme instanceof PersonalAccessScheme)
		{
			accessSchemeService.remove(service.findById(oldScheme.getClass(), oldScheme.getId()));
		}

		//Удаляем данные о персональном менеджере, если они есть, и выбрана схема прав администратора.
		if (newScheme != null  && newScheme.getCategory().equals(AccessCategory.CATEGORY_ADMIN) &&
				(changeableBusinessEmployee.getManagerEMail() != null || changeableBusinessEmployee.getManagerId() != null ||
				 changeableBusinessEmployee.getManagerPhone() != null || changeableBusinessEmployee.getManagerLeadEMail() != null))
		{
			employeeService.clearManagerInfo(changeableBusinessEmployee);
		}

		if (modify)
		{
			//если изменилась категория, стираем все записи из таблицы связки (подразделений)
			employeeService.removeAllowedDepartments(changeableBusinessEmployee);

			//если нет новой схемы прав ничего не можем добавить. Не знаем что делать.
			if (newScheme != null)
			{

				String newCategory = newScheme.getCategory();
				//Добавляем одно подразделение в таблицу-связку
				if (newCategory.equals(AccessCategory.CATEGORY_EMPLOYEE))
				{
					employeeService.replicateEmployeeDepartment(changeableBusinessEmployee);
				}
				if (newCategory.equals(AccessCategory.CATEGORY_ADMIN))
				{
				    employeeService.replicateDepartments(changeableBusinessEmployee, changingBusinessEmployee);
				}
			}
		}
	}

	public void delete(com.rssl.phizic.gate.employee.Employee employee) throws GateException, GateLogicException
	{
		final Employee removedEmployee = ((EmployeeWrapper) employee).getEmployee();
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.lock(removedEmployee, LockMode.UPGRADE);
				session.lock(removedEmployee.getLogin(), LockMode.UPGRADE);
				BankLogin login = removedEmployee.getLogin();
				schemeOwnService.removeAllSchemes(login);
				employeeService.removeAllowedDepartments(removedEmployee);
				employeeService.remove(removedEmployee);
				return null;
			}
		});
	}

	public void lock(final Login lockedLogin, final com.rssl.phizic.gate.login.LoginBlock block) throws GateException, GateLogicException
	{
		final Employee businessInitiator = getInitiator();
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				UserBlocksValidator validator = new UserBlocksValidator();
				BlockingReasonType reasonType = BlockingReasonType.valueOf(block.getReasonType());
				BankLogin login = ((LoginWrapper) lockedLogin).getLogin();
				if (validator.checkIfBlockOfThisTypeDoesntExist(login, reasonType))
				{
					securityService.lock(login, DateHelper.toDate(block.getBlockedFrom()), DateHelper.toDate(block.getBlockedUntil()),
							reasonType, block.getReasonDescription(), businessInitiator.getLogin().getId());
					return null;
				}

				String message = "Данный клиент заблокирован. Снимите существующую блокировку и назначьте клиенту ";
				if (block.getBlockedUntil() == null)
					message += "бессрочную блокировку.";
				else
					message += "временную блокировку.";
				throw new GateLogicException(message);
			}
		});
	}

	public List<com.rssl.phizic.gate.login.LoginBlock> unlock(final Login login) throws GateException, GateLogicException
	{
		return execute(new HibernateAction<List<com.rssl.phizic.gate.login.LoginBlock>>()
		{
			public List<com.rssl.phizic.gate.login.LoginBlock> run(Session session) throws Exception
			{
				BankLogin bankLogin = ((LoginWrapper) login).getLogin();
				Employee employee = employeeService.findByLogin(bankLogin);
				BankLogin employeeLogin = employee.getLogin();
				AccessScheme scheme = schemeOwnService.findScheme(employeeLogin, AccessType.employee);
				checkAdminCount(employee, scheme, null);
				SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
				Calendar lastLogonDate = employeeLogin.getLastLogonDate();
				long timeToBlock = securityConfig.getTimeToBlock();
				if(lastLogonDate == null || timeToBlock > 0 && DateHelper.diff(Calendar.getInstance(), lastLogonDate) > timeToBlock)
				{
					((LoginBase) employeeLogin).setLastLogonDate(Calendar.getInstance());
					employeeService.update(employee);
				}
				securityService.unlock(bankLogin, false, null, Calendar.getInstance().getTime());
				return Collections.emptyList();
			}
		});
	}

	public Login changePassword(final Login login) throws GateException, GateLogicException
	{
		return execute(new HibernateAction<Login>()
		{
			public Login run(Session session) throws Exception
			{
				UserPasswordChanger userPasswordChanger = new UserPasswordChanger(true);
				userPasswordChanger.changePassword(((LoginWrapper) login).getLogin(), login.getPassword().toCharArray());
				return login;
			}
		});
	}

	public void selfPasswordChange(final String password) throws GateException, GateLogicException
	{
		try
		{
			CommonLogin initiatorLogin = AuthModule.getAuthModule().getPrincipal().getLogin();
			String initiatorPassword =  securityService.getPasswordHash(initiatorLogin);
			String passwordHash = new String(new ManualPasswordGenerator(password).newPassword(0, null));
			if(initiatorPassword.equals(passwordHash))
				throw new GateLogicException("Введенный пароль совпадает с предыдущим");

			UserPasswordChanger userPasswordChanger = new UserPasswordChanger(false);
			userPasswordChanger.changePassword(initiatorLogin, password.toCharArray());
		}
		catch (SecurityDbException e)
		{
			throw new GateException(e);
		}
	}

	public List<AllowedDepartment> getAllowedDepartments(com.rssl.phizic.gate.employee.Employee employee) throws GateException, GateLogicException
	{
		Employee empl = ((EmployeeWrapper)employee).getEmployee();
		try
		{
			List<AllowedDepartments> allowedDepartments = departmentService.getAllowedDepartments(empl);
			List<AllowedDepartment> gateAllowedDepartments = new ArrayList<AllowedDepartment>();
			for(AllowedDepartments allowed : allowedDepartments)
				gateAllowedDepartments.add(new AllowedDepartment(allowed.getTB(), allowed.getOSB(), allowed.getVSP()));
			return gateAllowedDepartments;
		}
		catch(BusinessException be)
		{
			throw new GateException(be);
		}
	}

    private void check(final Employee employee, String queryName) throws Exception
    {
        BeanQuery query = new BeanQuery(queryName);
        query.setParameter("loginId",employee.getLogin().getId());
        Integer count = query.executeUnique();
        if (count!=null && count != 0)
            throw new BusinessLogicException("Вы не можете добавить доступ к дочернему подразделению, если уже доступно родительское.");
    }

	public void saveAllowedDepartments(com.rssl.phizic.gate.employee.Employee employee, List<AllowedDepartment> addAllowedDepartments, List<AllowedDepartment> removeAllowedDepartments) throws GateException, GateLogicException
	{
		final Employee empl = ((EmployeeWrapper)employee).getEmployee();
		final List<AllowedDepartments> addDepartment = new ArrayList<AllowedDepartments>(addAllowedDepartments.size());
		for (AllowedDepartment allowedDepartment: addAllowedDepartments)
			addDepartment.add(new AllowedDepartments(empl.getLogin().getId(), allowedDepartment.getTb(), allowedDepartment.getOsb(), allowedDepartment.getVsp()));
		final List<AllowedDepartments> removeDepartment = new ArrayList<AllowedDepartments>(addAllowedDepartments.size());
		for (AllowedDepartment allowedDepartment: removeAllowedDepartments)
			removeDepartment.add(new AllowedDepartments(empl.getLogin().getId(), allowedDepartment.getTb(), allowedDepartment.getOsb(), allowedDepartment.getVsp()));

		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				service.addList(addDepartment);
				service.removeList(removeDepartment);
                session.flush();
                check(empl, CHECK_TB_QUERY_NAME);
                check(empl, CHECK_OSB_QUERY_NAME);
                check(empl, CHECK_VSP_QUERY_NAME);
				return null;
			}
		});
	}

	private <T> T execute(HibernateAction<T> action) throws GateException, GateLogicException
	{
		try
		{
			return getExecutor().execute(action);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private HibernateExecutor getExecutor()
	{
		return HibernateExecutor.getInstance();
	}
}
