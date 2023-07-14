package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.notification.NotificationService;
import com.rssl.phizic.business.dictionaries.synchronization.notification.SynchronizationMode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.CSAAdminEmployeeContext;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.MultiNodeEmployeeData;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 10.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер справочников для многоблочной структуры
 */

public final class MultiBlockModeDictionaryHelper
{
	private static final String MAIN_INSTANCE_NAME = null;

	private static final DepartmentService departmentService = new DepartmentService();
	private static final NotificationService notificationService = new NotificationService();

	/**
	 * @return true -- мультиблочный режим
	 */
	public static boolean isMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}

	/**
	 * @return true -- мультиблочный режим работы ФОС
	 */
	public static boolean isMailMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMailMultiBlockMode();
	}

	/**
	 * @return имя инстанса модели БД
	 */
	public static String getDBInstanceName()
	{
		if(isMultiBlockMode())
			return getDictionaryLogInstanceName();
		return MAIN_INSTANCE_NAME;
	}

	/**
	 * @return имя инстанса БД, в который идет запись логов об изменении справочника
	 */
	public static String getDictionaryLogInstanceName()
	{
		return StringHelper.getNullIfEmpty(ConfigFactory.getConfig(CSAAdminGateConfig.class).getDictionaryInstance());
	}

	/**
	 * @return имя инстанса модели БД
	 */
	public static boolean needExternalCheck()
	{
		return !isMultiBlockMode();
	}

	/**
	 * оповестить блоки о необходимости синхронизации справочников
	 * @param dictionaryClass класс справочника
	 * @param <T> тип сущностей справочника
	 */
	public static <T> void updateDictionary(Class<T> dictionaryClass)
	{
		if (!isMultiBlockMode())
			return;

		notificationService.notify(dictionaryClass, SynchronizationMode.SOFT);
	}

	/**
	 * получить локальную версию подразделения
	 * @param departmentId идентификатор подразделения
	 * @return локальная версия подразделения
	 * @throws BusinessException
	 */
	public static Department getLocalDepartment(Long departmentId) throws BusinessException
	{
		Department department = departmentService.findById(departmentId, getDBInstanceName());
		if(department == null)
			throw new BusinessException("В CSAAdmin не найден департамент с id = "+ departmentId);

		if (!isMultiBlockMode())
			return department;

		Code departmentCode = department.getCode();
		Department localDepartment = departmentService.findByCode(departmentCode);
		if(department == null)
			throw new BusinessException("В блоке не найден департамент с кодом = "+ departmentCode.getId());

		return localDepartment;
	}

	/**
	 * @return данные сотрудника
	 */
	public static MultiNodeEmployeeData getEmployeeData()
	{
		if (MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return CSAAdminEmployeeContext.getData();
		else
			return EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	}
}
