package com.rssl.phizic.web.util;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import javax.servlet.http.HttpSession;

/**
 * @author egorova
 * @ created 16.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentViewUtil
{
	private static final DepartmentService departmentService = new DepartmentService();

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/*
	* Функция getDepartmentById возвращает подразделение по id
	*/
	public static Department getDepartmentById(Long id)
	{
		try
		{
			return departmentService.findById(id);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения подразделения по id", e);
			return null;
		}
	}

	/*
	* Функция getDepartmentById возвращает подразделение по id
	*/
	public static Department getDepartmentByIdConsiderMultiBlock(Long id)
	{
		try
		{
			return departmentService.findById(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (Exception e)
		{
			log.error("Ошибка определения подразделения по id", e);
			return null;
		}
	}

	/**
	 * Возвращает номер ТБ по его идентификатору.
	 * @param id мдентификатор
	 * @return номер ТБ
	 */
	public static String getTbNumberByIdConsiderMultiBlock(Long id)
	{
		try
		{
			return departmentService.getNumberTB(id, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch (Exception e)
		{
			log.error("Ошибка определения подразделения по id", e);
			return null;
		}
	}

	/*
	* Функция getTerBank возвращает Территориальный банк по коду подразделения
	*/
	public static Department getTerBank(Code code)
	{
		try
		{
			Department department = null;

			department = departmentService.findByCode(code);

			return departmentService.getTB(department);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения Территориального Банка. ", e);
			return null;
		}
	}

	/*
	* Функция isService возвращает true если подразделение с id подключено к Сбербанку Онлайн
	*/
	public static Boolean isService(com.rssl.phizic.business.departments.Department department)
	{
		if (department == null)
			return null;
		try
		{
			return department.isService();
		}
		catch (Exception e)
		{
			log.error("Ошибка определения проверки подключенности подразделения к Сбербанку онлайн", e);
			return null;
		}
	}
	/*
	* Функция hasClients возвращает true если подразделение с id имеет клиентов(со статусом "Активный" или "Подключение")
	*/
	public static Boolean hasClients(com.rssl.phizic.business.departments.Department department)
	{
		try
		{
			if(departmentService.CountClients(department.getId()) > 0)
			{
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения списка клиентов в подразделении", e);
			return null;
		}
	}

	/*
	* Функция getOSB возвращает ОСБ для текущего подразделения
	*/
	public static Department getOSB(Department department)
	{
		if (department == null)
		{
			return null;
		}
		try
		{
			if (DepartmentService.isTB(department))
			{
				return null; //использовать значения по умолчанию
			}
			if (DepartmentService.isOSB(department))
			{
				return department;
			}
			return departmentService.getDepartment(department.getRegion(), department.getOSB(), null);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения ОСБ для подразделения", e);
			return null;
		}
	}

	/**
	 * @param departmentId - id подразделения
	 * @return - возвращается осб для текущего подразделения
	 */
	public static Department getOsb(String departmentId)
	{
		if (StringHelper.isEmpty(departmentId))
			return null;
		try
		{
			return getOSB(departmentService.findById(Long.valueOf(departmentId)));
		}
		catch (Exception e)
		{
			log.error("Ошибка определения ОСБ для подразделения", e);
			return null;
		}
	}

	public static String getNameFromOsb(Department department)
	{
		return department.getName();
	}

	public static String getBicFromOsb(Department department)
	{
		return department.getBIC();
	}

	/**
	 * @return получить бик по умолчанию
	 */
	public static String getDefaultBankBic()
	{
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		return bankContext.getBankBIC();
	}

	/**
	 * @return получить имя банка по умолчанию
	 */
	public static String getDefaultBankName()
	{
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		return bankContext.getBankName();
	}

	/*
	* Функция getCorrByBIC возвращает корр. счет по БИК
	*/
	public static java.lang.String getCorrByBIC(java.lang.String BIC)
	{
		try
		{
			BankDictionaryService bankService = new BankDictionaryService();
			ResidentBank bank = bankService.findByBIC(BIC);
			if (bank != null)
			{
				return bank.getAccount();
			}
			return null;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения корр. счета по БИК", e);
			return "";
		}
	}

	/**
	 * Определение подразделения к которому привязан текущий пользователь
	 * @return подразделени
	 */
	public static Department getCurrentDepartment()
	{
		try
		{
			// получаем текущего пользователя и определяем департамент, к которому он привязан
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;

			return personData.getDepartment();
		}
		catch (Exception e)
	    {
	        log.error("Ошибка получения данных департамента клиента", e);
		    return null;
	    }
	}

	/**
	 * @return номера тербанка для текущего пользователя
	 */
	@Deprecated //по идее необходимо брать из контекста пользователя, но при протухании сессии контекст пуст, а в сессии данные ещё есть
	public static Long getCurrentDepartmentTBCode(HttpSession session)
	{
		try
		{
			Long departmentId = null;
			EmployeeData employeeData = (EmployeeData) session.getAttribute(EmployeeData.class.getName());
			if(employeeData != null)
				departmentId = employeeData.getEmployee().getDepartmentId();
			
			PersonData personData = (PersonData)session.getAttribute(StaticPersonData.class.getName());
			if(personData != null)
				departmentId = personData.getPerson().getDepartmentId();

			if(departmentId == null) // если не удалось получить номер департамента, то пользователь не привязан к департаменту
				return null;

			Department department = null;
			if (personData != null)
				department = personData.getDepartment();
			else
				department = employeeData.getDepartment();
			String tbCode = department.getCode().getFields().get("region");
			return Long.parseLong(tbCode);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения номера тербанка для пользователя" + e);
			return null;
		}
	}

	/**
	 * Найти телефон департамента
	 * @return телефон департамента
	 */
	public static String getDepartmentPhoneNumber(GateExecutableDocument document)
	{
		if (document instanceof LoanCardOfferClaim)
		{
			LoanCardOfferClaim claim = (LoanCardOfferClaim) document;
			return getDepartmentPhoneNumber(claim.getDepartmentTb(), claim.getDepartmentOsb(), claim.getDepartmentVsp());
		}

		if (document instanceof LoanCardClaim)
		{
			LoanCardClaim claim = (LoanCardClaim) document;
			return getDepartmentPhoneNumber(claim.getTb(), claim.getOsb(), claim.getVsp());
		}

		return StringUtils.EMPTY;
	}

	/**
	 * Найти телефон департамента
	 * @param tb номер тб
	 * @param osb номер osb или null
	 * @param vsp номер vsp или null
	 * @return телефон департамента
	 */
	public static String getDepartmentPhoneNumber(final String tb, final String osb, final String vsp)
	{
		try
		{
			ExtendedDepartment department = departmentService.getDepartment(tb, osb, vsp);
			return department.getTelephone();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения департамента. ", e);
			return null;
		}
	}

	/**
	 * Получает название департамента
	 * @param tb тб
	 * @param osb осб
	 * @param vsp всп
	 * @return название департамента
	 */
	public static String getDepartmentName(String tb, String osb, String vsp)
	{
		try
		{
			Department department = departmentService.getDepartment(tb, osb, vsp);
			return department.getName();
		}
		catch (Exception ex)
		{
			log.error("Ошибка получения департамента. ", ex);
	        return "";
		}

	}

	/**
	 * Имена ТБ, через ','.
	 * @param tbList Список кодов TB.
	 * @return
	 */
	public static String getTbNames(List<String> tbList)
	{
		StringBuilder stringBuilder = new StringBuilder();
		int size = tbList.size();
		for (int i=0; i < size; i++)
		{
			String name = getDepartmentName(tbList.get(i),null,null);
			stringBuilder.append(name);
			if (i+1 != size)
				stringBuilder.append(',');
		}
		return stringBuilder.toString();
	}

	/**
	 * @return Список всех кодов головных подразделений.
	 */
	public static List<String> getAllTb()
	{
		try
		{
			return departmentService.getTerbanksNumbers();
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения департамента.");
			return null;
		}
	}

	/**
	 * Определение подразделения , к которому привязан текущий пользователь
	 * @return подразделени. Определение подраздления происходит из контекста. Используем для мест, где результат получения подразделения не особо и важен: для логов, вывод имен...
	 *
	 */
	public static Department getCurrentDepartmentFromContext()
	{
		try
		{
			// получаем текущего пользователя и определяем департамент, к которому он привязан
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;
			return  personData.getDepartment();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения данных департамента клиента", e);
			return null;
		}
	}

	/**
	 * @return текущий тербанк. Тербанк берется из контекста пользователя
	 */
	public static Department getCurrentTerbankFromContext()
	{
		try
		{
			// получаем текущего пользователя и определяем департамент, к которому он привязан
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData == null)
				return null;
			return  personData.getTb();
		}
		catch (Exception ex)
		{
			log.error("Ошибка получения тербанка клиента. ", ex);
			return null;
		}
	}

	/**
	 * @return имя текущего тербанка. Тербанк берется из контекста пользователя
	 */
	public static String getCurrentTerbankName()
	{
		try
		{
			Department department = getCurrentTerbankFromContext();
			if (department != null)
				return department.getName();
			else
				return null;
		}
		catch (Exception ex)
		{
			log.error("Ошибка имени тербанка клиента. ", ex);
			return "";
		}

	}

	/**
	 * @param tbCode код тб.
	 * @return имя тербанка.
	 */
	public static String getCurrentTerbankNameByCode(String tbCode)
	{
		try
		{
			return  DepartmentViewUtil.getDepartmentName(tbCode, null, null);
		}
		catch (Exception ex)
		{
			log.error("Ошибка имени тербанка клиента. ", ex);
			return "";
		}
	}
}
