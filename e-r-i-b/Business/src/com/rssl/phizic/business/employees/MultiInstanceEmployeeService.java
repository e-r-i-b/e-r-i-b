package com.rssl.phizic.business.employees;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.MultiInstanceDepartmentService;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.config.SecurityConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author eMakarov
 * @ created 10.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceEmployeeService
{
	private static final MultiInstanceSimpleService simpleService   = new SimpleService();
    private static final MultiInstanceSecurityService securityService = new SecurityService();
	private static final MultiInstanceDepartmentService multiInstanceDepartmentService = new MultiInstanceDepartmentService();
	private static final int ORACLE_IN_LIMIT = 1000;

	public void remove(final Employee employee, final String instanceName) throws BusinessException
    {
        try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    simpleService.remove(employee, instanceName);
                    securityService.markDeleted(employee.getLogin(), instanceName);
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

    public Employee add(Employee employee, String instanceName) throws BusinessException
    {
        return simpleService.add(employee, instanceName);
    }

    public Employee update(Employee employee, String instanceName) throws BusinessException
    {
        return simpleService.update(employee, instanceName);
    }

    public Employee findById(Long id, String instanceName) throws BusinessException
    {
        return simpleService.findById(EmployeeImpl.class, id, instanceName);
    }

	public CommonLogin findLoginById(Long id, String instanceName) throws BusinessException
    {
        return simpleService.findById(CommonLogin.class, id, instanceName);
    }

	/**
	 * Поиск по имени логина
	 * @param userId
	 * @return
	 * @throws BusinessException
	 */
	public Employee findByUserId(String userId, String instanceName) throws BusinessException
	{
	    BankLogin login;

	    try
	    {
	        login = securityService.getBankLogin(userId, instanceName);
	    }
	    catch(Exception e)
	    {
	        throw new BusinessException(e);
	    }

		return findByLogin(login, instanceName);
	}


	/**
	 * Найти сотрудника банка по его логину
	 * @param login логин сотрудника банка
	 * @return сотрудник банка
	 */
	public Employee findByLogin(BankLogin login, String instanceName) throws BusinessException
	{
		if ( login == null )
		    throw new BusinessException("Ошибка поиска сотрудника. Не задан логин.");

		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		Employee employee    = null;
		String   superUserId = securityConfig.getDefaultAdminName();

		if ( superUserId != null && superUserId.equals(login.getUserId()) )
		{
			employee = getSuperEmployee(login);
		}
		else
		{
	        DetachedCriteria criteria = DetachedCriteria.forClass(EmployeeImpl.class).add(Expression.eq("login", login));
            employee = simpleService.findSingle(criteria, instanceName);
		}

		return employee;
	}

	/**
	 * Найти сотрудника банка по его managerId
	 * @param managerId - managerId сотрудника банка
	 * @return сотрудник банка
	 */
	public Employee findByManagerId(final String managerId, String instanceName) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Employee>()
			{
				public Employee run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.employees.findByManagerId");
					query.setParameter("managerId", managerId);
					return (Employee) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение всех доступных сотруднику подразделений
	 * @param employee - сотрудник
	 * @param instanceName - имя экземпляра БД
	 * @return набор доступных подразделений без учета иерархии
	 * @throws BusinessException
	 */
	public Set<Department> getAllowedDepartments(final Employee employee, String instanceName) throws BusinessException
	{
		try
		{
			return  HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Set<Department>>()
			{
				public Set<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getAllowedDepartments");
					query.setParameter("loginId", employee.getLogin().getId())
						 .setParameter("haveAllTBAccess", 0);

					//noinspection unchecked
					List<Department> listDepartments = query.list();
					Set<Department> setDepartments = new HashSet(listDepartments);
					return setDepartments;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * Удаление всех подразделений, привязанных к сотруднику (при удалении сотрудника)
	 * @param employee - сотрудник
	 * @param instanceName - имя экземпляра БД
	 * @throws BusinessException
	 */
	public void removeAllowedDepartments(final Employee employee, String instanceName) throws BusinessException
	{
		try
        {
            HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.removeAllowedDepartments");
					query.setParameter("loginId", employee.getLogin().getId());
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


	/**
	 * Добавляем доступное подразделение (без дочерних) сотруднику
	 * @param employee - сотрудник
	 * @param allowedDepartments - подразделение, для добавления
	 * @param instanceName - имя экземпляра БД
	 * @throws BusinessException
	 */
	public void  addAllowedDepartment(Employee employee, List<AllowedDepartments> allowedDepartments, String instanceName) throws BusinessException
	{


		try
		{
			for(AllowedDepartments allowedDepartment : allowedDepartments)
			{
				allowedDepartment.setLoginId(employee.getLogin().getId());
			}
			simpleService.addList(allowedDepartments, instanceName);
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


	/**
	 * Добавляем доступное подразделение сотруднику. Переносим подразделение, к которому привязан сотрудник в таблицу-связку.
	 * @param employee - сотрудник
	 * @param instanceName - имя экземпляра БД
	 * @throws BusinessException
	 */
	public void  replicateEmployeeDepartment(Employee employee, String instanceName) throws BusinessException
	{
		MultiInstanceDepartmentService departmentService = new MultiInstanceDepartmentService();
		List<AllowedDepartments> allowedDepartments = departmentService.getAllowedDepartments(employee, instanceName);
		addAllowedDepartment(employee, allowedDepartments, instanceName);
	}

	public void replicateDepartments(Employee employee, Employee parentEmployee, String instanseName) throws BusinessException
	{
		MultiInstanceDepartmentService departmentService = new MultiInstanceDepartmentService();
		List<AllowedDepartments> allowedDepartments = departmentService.getAllowedDepartments(parentEmployee, instanseName);
		addAllowedDepartment(employee, allowedDepartments, instanseName);
	}


	private Employee getSuperEmployee(BankLogin login)
	{
		Employee employee;
		employee = new EmployeeImpl();
		employee.setLogin(login);
		employee.setCAAdmin(true);

		return employee;
	}

	/**
	 * Создает копию, если уже есть переписывает
	 * @param employeeLogin логин сотрудника
	 * @param instanceName имя экземпляра БД
	 * @return сохраненный объект
	 * @throws BusinessException
	 */
	public CommonLogin replicate(CommonLogin employeeLogin, String instanceName) throws BusinessException
	{
		return simpleService.replicate(employeeLogin, instanceName);
	}

	public int getNumberAdmins(final Long departmentId) throws BusinessException
	{
		try
		{
			int result;
			result = HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
			    public Integer run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.operations.ext.sevb.access.AssignEmployeeAccessOperation.getNumberAdmins");
				    query.setParameter("departmentId",departmentId);
				    return Integer.decode(query.uniqueResult().toString());
			    }
			});
			return result;
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

	 /**
	 * Узнаем, имеет ли сотрудник доступ ко всем подразделеням из списка
	 * @param loginId - идентификатор логина сотрдуника
	 * @param departments - список id подразделений
	 * @param instanceName - имя экземпляра БД
	 * @return false - нет доступа к сотруднику, true - есть
	 * @throws BusinessException
	 */
	public Boolean isDepartmentsAllowed(final Long loginId, final List<Long> departments, String instanceName) throws BusinessException
	{
		if(departments==null || departments.size()==0)
			return true;

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.isDepatmentsAllowed");
					query.setParameterList("departments", departments);
					query.setParameter("login", loginId);
					return  Integer.decode(query.uniqueResult().toString())  == departments.size();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	 /**
	 * Узнаем, имеет ли сотрудник доступ к подразделеню с переданными tb, osb, vsp
	 * @param loginId - идентификатор логина сотрдуника
	 * @param tb    - ТБ
	 * @param osb   - ОСБ
	 * @param vsp   - ВСП
	 * @param instanceName - имя экземпляра БД
	 * @return false - нет доступа к подразделению, true - есть
	 * @throws BusinessException
	 */
	public Boolean isDepartmentsAllowedByCode(final Long loginId, final String tb, final String osb, final String vsp, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.isDepartmentsAllowedByCode");
					query.setString("tb", tb);
					query.setString("osb", osb);
					query.setString("vsp", vsp);
					query.setParameter("login", loginId);
					return BooleanUtils.isTrue((Boolean) query.uniqueResult());
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	 /**
	 * Узнаем, имеет ли сотрудник доступ ко всем ТБ из списка,
	 * если среди tbs есть id департамента, который не является тербанком, то функция вернёт false  
	 * @param employeeLoginId - идентификатор сотрдуника
	 * @param tbs - список номров Тербанков
	 * @param instanceName - имя экземпляра БД
	 * @return false - нет доступа к сотруднику, true - есть
	 * @throws BusinessException
	 */
	public Boolean isTBsAllowed(final Long employeeLoginId, final Collection<String> tbs, String instanceName) throws BusinessException
	{
		if(CollectionUtils.isEmpty(tbs))
			return true;

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.isTBsAllowed");
					query.setParameterList("departments", tbs);
					query.setParameter("login", employeeLoginId);
					return  Long.parseLong(query.uniqueResult().toString()) == tbs.size();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Очищает данные о персональном менеджере.
	 * @param id id сотрудника
	 * @throws BusinessException
	 */
	public void clearManagerInfo(final Long id, String instanceName) throws BusinessException
	{
		try
        {
            HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery(Employee.class.getName() + ".clearManagerInfo");
					query.setParameter("employeeId", id);
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

	/**
	 * Обновление доступных подразделений на основе масок из ЦСА Админ
	 * @param employee - сотрудник
	 * @param gateAllowedDepartments - маски доступных подразделений из ЦСА Админ
	 * @param instanceName - имя экземпляра БД
	 * @throws BusinessException
	 */
	public void updateAllowedDepartmentByGate(final Employee employee, final List<com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment> gateAllowedDepartments,final String instanceName) throws BusinessException
	{
		try
		{
			final List<AllowedDepartments> allowedDepartments = multiInstanceDepartmentService.getAllowedDepartments(employee, instanceName);
			final List<AllowedDepartments> gateDepartments = new ArrayList<AllowedDepartments>();
			for (com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment gateAllowedDepartment : gateAllowedDepartments)
			{
				gateDepartments.add(new AllowedDepartments(employee.getLogin().getId(), gateAllowedDepartment.getTb(), gateAllowedDepartment.getOsb(), gateAllowedDepartment.getVsp()));
			}

			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Iterator<AllowedDepartments> addIt = gateDepartments.iterator();
					Iterator<AllowedDepartments> deleteIt = allowedDepartments.iterator();
					for (; addIt.hasNext() || deleteIt.hasNext(); )
					{
						if (addIt.hasNext())
						{
							AllowedDepartments addAllowedDep = addIt.next();
							if (!allowedDepartments.contains(addAllowedDep))
								session.save(addAllowedDep);
						}
						if (deleteIt.hasNext())
						{
							AllowedDepartments remAllowedDep = deleteIt.next();
							if (!gateDepartments.contains(remAllowedDep))
								session.delete(remAllowedDep);
						}
					}
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
