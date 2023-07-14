package com.rssl.phizic.business.departments;

import com.rssl.phizic.TBRenameDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.allowed.AllowedDepartments;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 14.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentService extends MultiInstanceDepartmentService
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.departments.";

	public void addOrUpdate(Department department) throws BusinessException, DublicateDepartmentException
	{
		super.addOrUpdate(department, null, null);
	}

	public Department findById(Long departmentId) throws BusinessException
	{
		return super.findById(departmentId, null);
	}

	/**
	 * Ищем по synchKey в нашей базе
	 * @param id - уникальный ключ для синхронизации
	 * @return подразделение
	 * @throws BusinessException
	 */
	public Department findBySynchKey(final String id) throws BusinessException
	{
		return super.findBySynchKey(id, null);
	}

	/**
	 * Ищем по коду в нашей базе
	 * @param code - код офиса, к которому привязано подразделение
	 * @return подразделение
	 * @throws BusinessException
	 */
	public Department findByCode(final Code code) throws BusinessException
	{
		return super.findByCode(code, null);
	}

	/**
	 * Поиск подразделения по офису
	 * @param office - офис
	 * @return подразделение
	 * @throws BusinessException
	 */
	public Department findByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			return null;
		}
		if (office instanceof Department)
		{
			return (Department) office;
		}
		return findByCode(office.getCode());
	}

	public void remove(Department department) throws BusinessException, BusinessLogicException
	{
		super.remove(department, null);
	}

	/**
	 * @deprecated Какой-то недокументированный бред.
	 */
	@Deprecated
	public Integer getLevel(Department department)
	{
		if (department == null)
		{
			return 0;
		}
		if (isTB(department))
		{
			return 1;
		}
		if (isOSB(department))
		{
			return 2;
		}

		if (isVSP(department))
		{
			return 3;
		}
		throw new IllegalArgumentException("Невозможно определить уровень для подраздления " + department.getId());
	}

	/**
	 * функция CountClients возвращает для подразделения с departmentId клиентов (со статусом "Активный" или "Подключение").
	 */
	public int CountClients(final Long departmentId)  throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findPersonsByDepartmentId");
					query.setParameter("departmentId", departmentId);
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
	 * Возвращает регионы доступных тербанков
	 * @param loginId иденитфикатор логина сотрудника
	 * @return регионы
	 * @throws BusinessException
	 */
	public List<String> getAllowedTerbanksNumbers(final Long loginId)  throws BusinessException
	{
		return super.getAllowedTerbanksNumbers(loginId,null);
	}

	/**
	 * @return список номеров ТБ
	 */
	public List<String> getTerbanksNumbers()  throws BusinessException
	{
		return super.getTerbanksNumbers(null);
	}

	/**
	 * @return список ТБ к которым имеет доступ сотрудник
	 * @param loginId идентификатор логина сотрудника
	 */
	public List<Department> getTerbanks(final Long loginId)  throws BusinessException
	{
		return super.getTerbanks(loginId,null);
	}

	/**
	 * @return список ТБ
	 */
	public List<Department> getTerbanks() throws BusinessException
	{
		return super.getTerbanks(null);
	}

	/**
	 * @return список идентификаторов ТБ
	 * @throws BusinessException
	 */
	public List<Long> getTerbanksIds() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(ExtendedDepartment.class)
							.add(Restrictions.isNotNull("code.region"))
							.add(Restrictions.isNull("code.branch"))
							.add(Restrictions.isNull("code.office"))
							.setProjection(Projections.id());

					return (List<Long>) criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает ТБ по номеру (region) в БД (не по id)
	 * Считаем департамент ТБ, если  у него region существует, а branch и office is null
	 * @param region - номер ТБ
	 * @return - возвращает найденный ТБ, либо null
	 * @throws BusinessException
	 */
	public Department getDepartmentTBByTBNumber(String region) throws BusinessException
	{
		return getDepartment(region, null, null);
	}

	/**
	 * Рекурсивная функция поиска тб для переданного департамента
	 * Считается, что мы нашли ТБ - если родитель равен Null
	 * @param department - департамент, для которого ищем ТБ
	 * @return ТБ
	 */
	public Department getTB(Department department) throws BusinessException
	{
		return getTB(department, null);
	}

	/**
	 * Функция поиска тб для переданного id департамента
	 * Считается, что мы нашли ТБ - если родитель равен Null
	 * @param id -id  департамента, для которого ищем ТБ
	 * @return ТБ
	 */
	public Department getTB(Long id) throws BusinessException
	{
		Department department = findById(id);
		return getTB(department);
	}

	/**
	 * @param department департамент
	 * @param loginId идентификатор логина сотрудника
	 * @return доступен ли департамент сотруднику
	 * @throws BusinessException
	 */
	public boolean isDepartmentAllowed(final Department department, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.departments.allowed.isDepartmentAllowed");
					query.setParameter("loginId", loginId);
					query.setParameter("TB", department.getRegion());
					query.setParameter("OSB", department.getOSB());
					query.setParameter("OFFICE", department.getVSP());
					query.setMaxResults(1);
					return query.list().size() > 0;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверяет доступность тербанка, к которому относится департамент.
	 * @param department департамент
	 * @param loginId идентификатор логина сотрудника
	 * @return возвращает true, если доступно хотя бы одно подразделение тербанка, даже если сам тербанк недоступен.
	 *          возвращает false, если недоступно ни одно из подразделений тербанка, включая сам тербанк.
	 * @throws BusinessException
	 */
	public boolean isTBAllowed(final Department department, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = DataBaseTypeQueryHelper.getNamedQuery(session, "com.rssl.phizic.business.departments.allowed.isTBAllowed");
					query.setParameter("loginId", loginId);
					query.setParameter("TB", department.getRegion());
					query.setMaxResults(1);
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает список всех подразделений ВСП, которые выдают кредитную карту (для данных подразделений должны также существовать записи в справочнике перекодировок
	 * СПООБК с ненулевым полем DESPATCH).
	 *
	 * @param filter - параметры фильтрации
	 * @throws BusinessException
	 */
	public List<Office> getAllowedCreditCardOffices(final String filter) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Office>>()
			{
				public List<Office> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.departments.getAllowedReissueCardOffices");
					query.setParameter("filter", filter);
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
	 * Список тербанков для промоутеров при их регистрации и начале работы
	 * @return List<ExtendedDepartment> - список всех тербанков для пормоутера
	 * @throws BusinessException
	 */
	public List<Department> getPromoterTbList() throws BusinessException
	{
		return getTerbanks();
	}

	/**
	 * Поиск тб для переданного офиса
	 * Считается, что мы нашли ТБ - если родитель равен Null
	 * @param office - офис, для которого ищем ТБ
	 * @return ТБ
	 */
	public Department getTBByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			throw new IllegalArgumentException("Офис не может быть null");
		}

		if (isTB(office) && office instanceof Department)
		{
			return (Department) office;
		}
		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return getDepartment(sbrfOfficeCodeAdapter.getRegion(), null, null);
	}

	/**
	 * Поиск ОСБ для переданного офиса
	 * @param office - офис, для которого ищем ТБ
	 * @return ОСБ
	 */
	public Department getOSBByOffice(final Office office) throws BusinessException
	{
		if (office == null)
		{
			throw new IllegalArgumentException("Офис не может быть null");
		}

		if (isTB(office))
		{
			throw new IllegalArgumentException("Невозможно найти ОСБ для ТБ");
		}
		if (isOSB(office) && office instanceof Department)
		{
			return (Department) office;
		}
		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return getDepartment(sbrfOfficeCodeAdapter.getRegion(), sbrfOfficeCodeAdapter.getBranch(), null);
	}

	/**
	 * @param tb номер тб
	 * @param osb номер osb или null
	 * @param vsp номер vsp или null
	 * @return департамент
	 * @throws BusinessException
	 */
	public ExtendedDepartment getDepartment(String tb, String osb, String vsp) throws BusinessException
	{
		return super.getDepartment(tb,osb,vsp,null);
	}

	/**
	 * Список подразделений всех подразделений для репликации
	 * @return список массивов из двух элементов: 0 - подразделения, 1 - типа узла
	 * @throws GateException
	 */
	public List<ExtendedDepartment> getTreeDepartments(final List<Long> ids) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedDepartment>>()
			{
				public List<ExtendedDepartment> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getReplicationDepartments");
					query.setParameterList("ids", ids);

					return (List<ExtendedDepartment>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список кодов подразделений по их идентификаторам
	 * @param ids список идентификаторов подразделений
	 * @return списко кодов подразедедлений
	 * @throws BusinessException
	 */
	public List<Code> getCodesByDepartmentIds(final List<Long> ids) throws BusinessException
	{
		log.debug("Получение списка кодов подразделений по их идентификаторам (входные параметры : ids = [" + StringUtils.join(ids, ',') + "])");

		if(CollectionUtils.isEmpty(ids))
			throw new IllegalArgumentException("ids не может быть пустым.");


		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Code>>()
			{
				public List<Code> run(Session session) throws Exception
				{
					log.debug("Получение списка кодов подразделений по их идентификаторам (запрос к базе : ids = [" + StringUtils.join(ids, ',') + "])");

					Query query = session.getNamedQuery(QUERY_PREFIX + "getCodesByNodeType");
					query.setParameterList("ids", ids);

					List<Code> codes = new ArrayList<Code>();
					for(Object[] codeFields : (List<Object[]>)query.list())
					{
						codes.add(new ExtendedCodeImpl(
								(String) codeFields[0],(String) codeFields[1],(String) codeFields[2]));
					}

					log.debug("Получение списка кодов подразделений по их идентификаторам (результат :" + getLogMessageByCodes(codes));
					return codes;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private String getLogMessageByCodes(List<Code> codes)
	{
		try
		{
			if(CollectionUtils.isEmpty(codes))
				return StringUtils.EMPTY;

			StringBuilder builder = new StringBuilder();
			for(Code code : codes)
			{
				if(MapUtils.isEmpty(code.getFields()))
					break;

				builder.append("(");
				boolean first = true;
				for(Map.Entry<String, String> entry : code.getFields().entrySet())
				{
					if(!first)
						builder.append(", ");
					else
						first = false;

					builder.append(entry.getKey()).append(" = ").append(entry.getValue());
				}

				builder.append(")");
			}

			return builder.toString();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return StringUtils.EMPTY;
		}
	}

	/**
	 * Получить реквизиты ТБ
	 *
	 * @param regionCode код ТБ
	 * @param offCode код отделения
	 * @return реквизиты ТБ
	 */
	public TerBankDetails findTerBankDetailsByCode(String regionCode, String offCode) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(TerBankDetails.class);
		criteria.add(Expression.eq("code", regionCode));
		criteria.add(Expression.eq("offCode", offCode));
		return simpleService.findSingle(criteria, null);
	}

	/**
	 * Проверяем код тербанка и заменяем, если это нужно для корректного поиска клиента в базе
	 * @param codeRegion код тербанка
	 * @return массив с номерами тербанков, в которых нужно искать клиента
	 */
	public static String[] getCorrectTBCodes(String codeRegion)
	{
		if (StringHelper.isEmpty(codeRegion))
			return new String[0];

		//CHG034816: делаем обратное преобразование от подчинённого(02) к основному(44) для корректного поиска клиента в базе.
		String tbCode = ConfigFactory.getConfig(TBRenameDictionary.class).getOldTbBySynonym(codeRegion);
		if (!StringHelper.isEmpty(tbCode))
			return new String[]{tbCode.substring(0,2)};

		// BUG027314: Объеденить коды 99 и 38 при поиске клиента
		if (codeRegion.equals("99") || codeRegion.equals("38"))
			return new String[]{"38", "99"};

		return new String[]{codeRegion};
	}

	/**
	 * Получить номер ТБ по id подразделения.
	 * @param id - id  департамента, для которого ищем номер ТБ
	 * @return номер ТБ
	 */
	public String getNumberTB(final Long id) throws BusinessException
	{
		return super.getNumberTB(id, null);
	}

	/**
	 * Возвращает список масок доступных департаментов
	 * @param empl сотрудник
	 * @return списокк масок
	 * @throws BusinessException
	 */
	public List<AllowedDepartments> getAllowedDepartments(Employee empl) throws BusinessException
	{
		return super.getAllowedDepartments(empl, null);
	}

	/**
	 * Есть ли у сторудника с login доступ ко всем департаменам
	 * @param loginId логин сотрудника
	 * @return да/нет
	 * @throws BusinessException
	 */
	public boolean isAllTBAccess(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
			    public Boolean run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery(DepartmentService.class.getName()+".isAllTBAccess");
					query.setParameter("loginId", loginId);
				    return ((Long)query.uniqueResult() > 0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить родительское подразделение переданного
	 * @param department подраздление
	 * @return родительское или null, если это ТБ
	 */
	public Department getParent(Department department) throws BusinessException
	{
		return super.getParent(department,null);
	}

	/**
	 * Является ли переданнный офис тербанком
	 * @param office офис
	 * @return да/нет
	 */
	public static boolean isTB(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("Офис не может быть null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

	/**
	 * Является ли переданнный офис OCБ
	 * @param office офис
	 * @return да/нет
	 */
	public static boolean isOSB(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("Офис не может быть null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

	/**
	 * Является ли переданнный офис VSP
	 * @param office офис
	 * @return да/нет
	 */
	public static boolean isVSP(Office office)
	{
		if (office == null)
		{
			throw new IllegalArgumentException("Офис не может быть null");
		}

		SBRFOfficeCodeAdapter sbrfOfficeCodeAdapter = new SBRFOfficeCodeAdapter(office.getCode());
		return StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getOffice()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getBranch()) && StringHelper.isNotEmpty(sbrfOfficeCodeAdapter.getRegion());
	}

    /**
     *
     * @param regionCodeTb
     * @return Список подразделений где можно получить кредитную карту
     * @throws BusinessException
     */
    public static List<ExtendedDepartment> getAllCreditCardOffices(final String regionCodeTb) throws BusinessException
    {
        return getAllCreditCardOffices(regionCodeTb, null, null, null);
    }

    /**
     *
     * @param regionCodeTb
     * @param name
     * @param address
     * @param reverseAddress
     * @return Список подразделений где можно получить кредитную карту
     * @throws BusinessException
     */
    public static List<ExtendedDepartment> getAllCreditCardOffices(final String regionCodeTb, final String name, final String address, final String reverseAddress) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedDepartment>>()
            {
                public List<ExtendedDepartment> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.operations.departments.CreditCardOfficeOperation.list");
                    query.setParameter("extra_regionCodeTB", regionCodeTb);
                    query.setParameter("extra_like_name", StringHelper.isEmpty(name)? "%" : name);
                    query.setParameter("extra_like_address", StringHelper.isEmpty(address)? "%" : address);
                    query.setParameter("extra_like_reverse_address", StringHelper.isEmpty(reverseAddress) ? "%" : reverseAddress);
                    return (List<ExtendedDepartment>) query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }
}

