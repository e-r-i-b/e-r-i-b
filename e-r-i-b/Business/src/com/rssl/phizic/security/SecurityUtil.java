package com.rssl.phizic.security;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.PermissionsProvider;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentIsNotServicedException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.pfp.configure.PersonalFinanceProfileService;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentAvailableService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 20.09.2005 Time: 13:39:33
 */
public class SecurityUtil
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final SegmentAvailableService segmentAvailableService = new SegmentAvailableService();
	private static final PersonalFinanceProfileService personalFinanceProfileService = new PersonalFinanceProfileService();
    private static final DepartmentService departmentService = new DepartmentService();

	public static final String SESSION_CONTEXT_KEY = "com.rssl.phizic.Context";
	public static final String LOGIN_COMPLETE_KEY  = "com.rssl.phizic.LoginComplete";

	private static String cryptoProviderName;

	/**
	 * Cоздать новый AuthModule для пользователя
	 * @param principal принципал для которого надо создать AuthModule
	 */
	public static void createAuthModule (UserPrincipal principal)
	{
		AuthModule authModule = new AuthModule(principal);
		AuthModule.setAuthModule(authModule);
	}

	/**
	 * Создать новый AuthModule для стадии
	 * @param principal принципал
	 * @param permissionsProvider поставщик прав
	 */
	public static void createAuthModule(UserPrincipal principal, PermissionsProvider permissionsProvider)
	{
		AuthModule tempModule = new AuthModule(principal, permissionsProvider);
		AuthModule.setAuthModule(tempModule);
	}

	/**
	 * Завершен ли процесс аутентификации для пользователя отправившего запрос
	 * @return true == завершен
	 */
	public static boolean isAuthenticationComplete()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalStateException("Нет сессии");
		Boolean attribute = (Boolean) store.restore(LOGIN_COMPLETE_KEY);
		return attribute!=null && attribute;
	}

	/**
	 * Установить признак завершенности процесса аутентификации
	 */
	public static void completeAuthentication()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new RuntimeException("Сессия не установлена");
		store.save(LOGIN_COMPLETE_KEY, true);
	}

	/**
	 * @return имя крипто провайдера или пустая строка
	 */
	public synchronized static String cryptoProviderName()
	{
		if(cryptoProviderName != null)
			return cryptoProviderName;

		try
		{
			cryptoProviderName = CryptoProviderHelper.getDefaultFactory().getName();
			return cryptoProviderName;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения имени крипто провайдера", e);
			return "";
		}
	}

	/**
	 * @return может ли клиент стучаться в модуль ПФП
	 */
	public static boolean hasAccessToPFP() throws BusinessException
	{
		if (!PermissionUtil.impliesService("ClientPfpEditService"))
			return false;

		ActivePerson activePerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		return isSegmentAvailable(activePerson) || personalFinanceProfileService.havePlaning(activePerson.getLogin());
	}

	/** 
	 * @param person клиент
	 * @return можем ли работать с данным клиентом.
	 */
	public static final boolean canWorkWhithPerson(ActivePerson person)
	{
		if(!PermissionUtil.impliesOperation("SearchVIPForPFPOperation", "EmployeePfpEditForVIPService") && person.getSegmentCodeType()== SegmentCodeType.VIP)
			return false;
		return true;
	}

	/**
	 * @param activePerson клиент для которого проверяем доступность
	 * @return есть ли ограничения на сегмент текущего пользователя (true - ограничений нет)
	 */
	private static boolean isSegmentAvailable(ActivePerson activePerson)
	{
		try
		{
			Boolean available = activePerson.isAvailableStartPFP();
			if (available == null)
			{
				available = segmentAvailableService.isSegmentAvailable(activePerson.getSegmentCodeType());
				activePerson.setAvailableStartPFP(available);
			}
			return available;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при проверке доступности функционала для семента клиента.", e);
			return false;
		}
	}

    /**
     * Проверка обслуживается ли департамент
     * @param person клиент для которого проверяем доступность
     * @throws com.rssl.phizic.business.BusinessException
     */
    public static void checkDepartmentIsServiced(Person person) throws BusinessException
    {
        if(person == null)
            return;

        Department department = departmentService.findById(person.getDepartmentId());

        if (department != null && !department.isService())
        {
            throw new DepartmentIsNotServicedException("Подразделение " +  department.getName() + " временно не обслуживается. Вход в систему запрещен");
        }

        return;
    }

	/**
	 * Проверка на подключение скриптов Касперского
	 * @return подключать ли скрипты
	 */
	public static boolean needKasperskyScript()
	{
		return ConfigFactory.getConfig(SecurityConfig.class).getNeedKasperskyScript();
	}
}
