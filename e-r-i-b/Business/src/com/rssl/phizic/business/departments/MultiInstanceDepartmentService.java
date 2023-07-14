package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceDepartmentService
{
	protected static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final String REGION_KEY = "region";
	public static final String BRANCH_KEY = "branch";
	public static final String OFFICE_KEY = "office";

	/**
	 * поиск по id подразделения
	 * @param departmentId идентификатор подразделения
	 * @param instanceName инстанс базы
	 * @return Найденное подразделение
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Department findById(Long departmentId, String instanceName) throws BusinessException
	{
		return simpleService.findById(Department.class, departmentId, instanceName);
	}

	/**
	 * Поиск по списку идентификаторов
	 * @param ids список идентификатор
	 * @return найденные подразделения
	 * @throws BusinessException
	 */
	public List<Department> findByIds(List<Long> ids) throws BusinessException
	{
		return simpleService.findByIds(Department.class, ids, null);
	}

	/**
	 * Добавления нового подразделения
	 * @param department - департамент, который добавляем
	 * @param instanceName - инстанс базы
	 * @param oldSynchKey - старый идентификатор
	 * @throws BusinessException
	 * @throws DublicateDepartmentException такое подразделение уже добавлено
	 */
	public void addOrUpdate(final Department department, String instanceName, final String oldSynchKey) throws BusinessException,DublicateDepartmentException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Department>()
			{
				public Department run(Session session) throws Exception
				{
					session.saveOrUpdate(department);
					dictionaryRecordChangeInfoService.addChangesToLog(session, department, ChangeType.update, oldSynchKey);
					session.flush();
					return department;
				}
			});

			receptionTimeService.addRecepientTime(department, instanceName);

		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateDepartmentException("Такое подразделение уже добавлено", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void remove(final Department department, final String instanceName) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        receptionTimeService.removeRecepientTime(department.getId(),instanceName);
			        dictionaryRecordChangeInfoService.addChangesToLog(department, ChangeType.delete);
			        Query query = session.getNamedQuery("com.rssl.phizic.business.departments.removeDepartment");
					query.setParameter("department", department);
			        query.executeUpdate();

		            return null;
		        }
		    });
		}
		catch (ConstraintViolationException e)
		{
		   throw new BusinessLogicException("Невозможно удалить подразделение. Оно либо имеет подчиненные либо к нему привязаны сотрудники и/или клиенты",e);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить
	 * @return
	 * @throws BusinessException
	 */
	public List<Department> getAll() throws BusinessException
	{
		return simpleService.getAll(Department.class, null);
	}

	/**
	 * Создает копию, если уже есть переписывает
	 * @param department подразделение
	 * @param instanceName имя экземпляра БД
	 * @return сохраненный объект
	 * @throws BusinessException
	 */
	public Department replicate(Department department, String instanceName) throws BusinessException
	{
		return simpleService.replicate(department, instanceName);
	}

	/**
	 * Найти подразделение по synchKey
	 * @param  synchKey офиса
	 * @param instanceName имя экземпляра БД
	 * @return подразделение
	 * @throws BusinessException
	 */
	public Department findBySynchKey(final String synchKey, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Department.class).
				add(Expression.eq("synchKey", synchKey));
		return simpleService.findSingle(criteria, instanceName);
	}

	/**
	 * Найти подразделение по коду
	 * @param code код офиса
	 * @return офис
	 * @throws BusinessException
	 */
    public Department findByCode (final Code code, String instanceName) throws BusinessException
    {
        try
        {
	        HibernateExecutor trnExecutor = HibernateExecutor.getInstance(instanceName);

	        //noinspection OverlyComplexAnonymousInnerClass
	        return trnExecutor.execute(new HibernateAction<Department>()
            {
                public Department run(Session session) throws Exception
                {
	                Map<String, String> properties = code.getFields();

	                if (MapUtils.isEmpty(properties))
                    {
                        String message = "Недостаточно критериев для поиска";
                        log.error(message);
                        throw new BusinessException(message);
                    }

					properties.put(REGION_KEY, ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(properties.get(REGION_KEY)));

					Department department = findDepartmentByOSB(session, properties);
					//todo. Костыль. Дело в том, что для карт вместо ОСБ приходит sbidnt. Поэтому требуется дополнительный поиск.
					//todo. НЕ в сбере будет падать. Убрать, как только из шины будет приходить корректный офис.
					return (department == null) ? findDepartmentBySBIDNT(session, properties) : department;
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	private Department findDepartmentBySBIDNT(Session session,  Map<String, String> parameters) throws BusinessException
	{
		Criteria criteria = getFindDepartmentCriteria(parameters.get(REGION_KEY), parameters.get(BRANCH_KEY), parameters.get(OFFICE_KEY), "SBIDNT").getExecutableCriteria(session);
		List<Department> departments = criteria.list();
		return CollectionUtils.isEmpty(departments) ? null : departments.get(0);
	}

	private Department findDepartmentByOSB(Session session, Map<String, String> parameters) throws BusinessException
	{
		return (Department) getFindByBankInfoCriteria(parameters).getExecutableCriteria(session).uniqueResult();
	}

	private void addEqStringParameter(DetachedCriteria criteria, String name, String value)
	{
		if (StringHelper.isNotEmpty(value))
		{
			criteria.add(Expression.eq(name, value));
		}
		else
		{
			criteria.add(Expression.isNull(name));
		}
	}

	/**
	 * получить criteria для поиска подразделений по bankInfo: region, branch, office
	 * @param parameters параметры
	 * @return criteria
	 * @throws BusinessException
	 */
	public DetachedCriteria getFindByBankInfoCriteria(Map<String, String> parameters) throws BusinessException
	{
		return getFindDepartmentCriteria(parameters.get(REGION_KEY),parameters.get(BRANCH_KEY), parameters.get(OFFICE_KEY));
	}

	/**
	 *
	 * получить criteria для поиска подразделений по bankInfo: region, branch, office
	 * @param tb тб
	 * @param osb осб
	 * @param vsp vsp
	 * @return criteria
	 * @throws BusinessException
	 */

	public DetachedCriteria getFindDepartmentCriteria(String tb, String osb, String vsp) throws BusinessException
	{

		return getFindDepartmentCriteria(tb, osb, vsp, "OSB");
	}

	private DetachedCriteria getFindDepartmentCriteria(String tb, String osb, String vsp, String branchPropertyName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ExtendedDepartment.class);

		addEqStringParameter(criteria, "code.region",       tb);
		simpleService.addNvlParameter(criteria, branchPropertyName,       osb);
		simpleService.addNvlParameter(criteria, "OFFICE",                 vsp);

		return criteria;
	}

	/**
	 * Возвращает список масок доступных департаментов
	 * @param empl сотрудник
	 * @param instanceName инстанс
	 * @return список масок
	 * @throws BusinessException
	 */
	public List<AllowedDepartments> getAllowedDepartments(Employee empl, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AllowedDepartments.class);
		criteria.add(Expression.eq("loginId", empl.getLogin().getId()));
		return simpleService.find(criteria, instanceName);
	}

	/**
	 * Рекурсивная функция поиска тб для переданного департамента
	 * Считается, что мы нашли ТБ - если родитель равен Null
	 * @param department - департамент, для которого ищем ТБ
	 * @param instanceName инстанс
	 * @return ТБ
	 */
	public Department getTB(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
			throw new BusinessException("Невозможно определить ТБ для незаданного департамента");

		if (isTB(department))
		{
			return department;
		}
		return getDepartment(department.getRegion(), null, null, instanceName);
	}

	/**
	 * Является ли офис TB
	 * @param department офис
	 * @return true - TB
	 */
	public static boolean isTB(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("Подразделение не может быть null");
		}
		return StringHelper.isEmpty(department.getVSP()) && StringHelper.isEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * Является ли офис ОСБ
	 * @param department офис
	 * @return true - ОСБ
	 */
	public static boolean isOSB(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("Подразделение не может быть null");
		}
		return StringHelper.isEmpty(department.getVSP()) && StringHelper.isNotEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * Является ли офис ВСП
	 * @param department офис
	 * @return true - ВСП
	 */
	public static boolean isVSP(Department department)
	{
		if (department == null)
		{
			throw new IllegalArgumentException("Подразделение не может быть null");
		}
		return StringHelper.isNotEmpty(department.getVSP()) && StringHelper.isNotEmpty(department.getOSB()) && StringHelper.isNotEmpty(department.getRegion());
	}

	/**
	 * для северного банка необходимо, чтобы для каждой внешней системы существовало лишь одно
	 * головное подразделение.
	 * @param externalSystemUUID - ууид внешней системы
	 * @return число головных подразделений для данной внешней системы.
	 */
	public int getMainDepartmentsCount(final String externalSystemUUID, String instanceName)  throws BusinessException
	{

		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Integer>()
			{
			    public Integer run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetMainDepartmentsCount");
				    query.setParameter("externalSystemUUID", externalSystemUUID);
				    return Integer.decode(query.uniqueResult().toString());
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return список ТБ
	 */
	public List<Department> getTerbanks(String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
			    public List<Department> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanks");
				    return query.list();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает регионы доступных тербанков
	 * @param loginId иденитфикатор логина сотрудника
	 * @param  instanceName - инстанс БД
	 * @return регионы
	 * @throws BusinessException
	 */
	public List<String> getAllowedTerbanksNumbers(final Long loginId, String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetAllowedTerbanksNumbers");
					query.setParameter("employeeLoginId", loginId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instanceName - инстанс БД
	 * @return список номеров ТБ
	 */
	public List<String> getTerbanksNumbers(String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanksNumbers");
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param employeeLoginId - идентификатор логина сотрудника
	 * @param instanceName - инстанс БД
	 * @return Список ТБ, к которым относятся доступные сотруднику подразделения.
	 * @throws BusinessException
	 */
	public List<Department> getTerbanksByAllowedDepartments(final Long employeeLoginId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getTerbanksByAllowedDepartments");
					query.setParameter("employeeLoginId", employeeLoginId);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return список ТБ к которым имеет доступ сотрудник
	 * @param loginId идентификатор логина сотрудника
	 * @param instanceName инстанс БД
	 */
	public List<Department> getTerbanks(final Long loginId, String instanceName)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
			    public List<Department> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetAllowedTerbanks");
				    query.setParameter("employeeLoginId", loginId);
				    return query.list();
			    }
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param tb тербанк
	 * @param instanceName инстанс БД
	 * @return список ОСБ заданного тербанка
	 */
	public List<Department> getOSBs(final String tb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getOSBs")
							.setParameter("tb", tb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param tb тербанк
	 * @param osb ОСБ
	 * @param instanceName инстанс БД
	 * @return список ВСП заданного тербанка
	 */
	public List<Department> getVSPs(final String tb, final String osb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Department>>()
			{
				public List<Department> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getVSPs")
							.setParameter("tb", tb)
							.setParameter("osb", osb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param tb номер тб
	 * @param osb номер osb или null
	 * @param vsp номер vsp или null
	 * @param instanceName инстанс БД
	 * @return департамент
	 * @throws BusinessException
	 */
	public ExtendedDepartment getDepartment(final String tb, final String osb, final String vsp, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ExtendedDepartment>()
			{
				public ExtendedDepartment run(Session session) throws Exception
				{
					Map<String, String> parameters = new HashMap<String, String>(3);
					parameters.put(REGION_KEY, StringUtils.strip(tb));
					parameters.put(BRANCH_KEY, StringHelper.isEmpty(osb) ? null : StringUtils.strip(osb));
					parameters.put(OFFICE_KEY, StringHelper.isEmpty(vsp) ? null : StringUtils.strip(vsp));

					return (ExtendedDepartment) getFindByBankInfoCriteria(parameters).getExecutableCriteria(session).uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ExtendedDepartment getBranch(final String tb, final String osb, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ExtendedDepartment>()
			{
				public ExtendedDepartment run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getBranch");
					query.setString("tb", tb);
					query.setString("osb", osb);
					return (ExtendedDepartment) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public boolean isExistsBranch(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
		{
			throw new IllegalArgumentException("Подраздление не может быть null");
		}
		if (isTB(department))
		{
			return true; //не проверяем
		}

		if (isOSB(department))
		{
			return true; //не проверяем
		}

		if (isVSP(department))
		{
			return getBranch(department.getRegion(), department.getOSB(), instanceName) != null;
		}
		throw new IllegalArgumentException("Невозиожно определить тип подраздления " + department.getId());
	}

	/**
	 * Получить родительское подразделение переданного
	 * @param department подраздление
	 * @param instanceName инстанс БД
	 * @return родительское или null, если это ТБ
	 */
	public Department getParent(Department department, String instanceName) throws BusinessException
	{
		if (department == null)
		{
			throw new IllegalArgumentException("Подраздление не может быть null");
		}
		if (isTB(department))
		{
			return null;
		}

		if (isOSB(department))
		{
			return getDepartment(department.getRegion(), null, null,instanceName);
		}

		if (isVSP(department))
		{
			return getDepartment(department.getRegion(), department.getOSB(), null, instanceName);
		}
		throw new IllegalArgumentException("Невозиожно определить тип подраздления " + department.getId());
	}

	/**
	 * Получить номер ТБ по id подразделения.
	 * @param id - id  департамента, для которого ищем номер ТБ
	 * @param instanceName инстанс БД
	 * @return номер ТБ
	 */
	public String getNumberTB(final Long id, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.GetNumberTbById");
					query.setParameter("departmentId", id);
					return (String)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
