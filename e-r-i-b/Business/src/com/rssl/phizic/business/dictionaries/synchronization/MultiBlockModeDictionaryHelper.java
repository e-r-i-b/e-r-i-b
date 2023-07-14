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
 * ������ ������������ ��� ������������ ���������
 */

public final class MultiBlockModeDictionaryHelper
{
	private static final String MAIN_INSTANCE_NAME = null;

	private static final DepartmentService departmentService = new DepartmentService();
	private static final NotificationService notificationService = new NotificationService();

	/**
	 * @return true -- ������������� �����
	 */
	public static boolean isMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}

	/**
	 * @return true -- ������������� ����� ������ ���
	 */
	public static boolean isMailMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMailMultiBlockMode();
	}

	/**
	 * @return ��� �������� ������ ��
	 */
	public static String getDBInstanceName()
	{
		if(isMultiBlockMode())
			return getDictionaryLogInstanceName();
		return MAIN_INSTANCE_NAME;
	}

	/**
	 * @return ��� �������� ��, � ������� ���� ������ ����� �� ��������� �����������
	 */
	public static String getDictionaryLogInstanceName()
	{
		return StringHelper.getNullIfEmpty(ConfigFactory.getConfig(CSAAdminGateConfig.class).getDictionaryInstance());
	}

	/**
	 * @return ��� �������� ������ ��
	 */
	public static boolean needExternalCheck()
	{
		return !isMultiBlockMode();
	}

	/**
	 * ���������� ����� � ������������� ������������� ������������
	 * @param dictionaryClass ����� �����������
	 * @param <T> ��� ��������� �����������
	 */
	public static <T> void updateDictionary(Class<T> dictionaryClass)
	{
		if (!isMultiBlockMode())
			return;

		notificationService.notify(dictionaryClass, SynchronizationMode.SOFT);
	}

	/**
	 * �������� ��������� ������ �������������
	 * @param departmentId ������������� �������������
	 * @return ��������� ������ �������������
	 * @throws BusinessException
	 */
	public static Department getLocalDepartment(Long departmentId) throws BusinessException
	{
		Department department = departmentService.findById(departmentId, getDBInstanceName());
		if(department == null)
			throw new BusinessException("� CSAAdmin �� ������ ����������� � id = "+ departmentId);

		if (!isMultiBlockMode())
			return department;

		Code departmentCode = department.getCode();
		Department localDepartment = departmentService.findByCode(departmentCode);
		if(department == null)
			throw new BusinessException("� ����� �� ������ ����������� � ����� = "+ departmentCode.getId());

		return localDepartment;
	}

	/**
	 * @return ������ ����������
	 */
	public static MultiNodeEmployeeData getEmployeeData()
	{
		if (MultiBlockModeDictionaryHelper.isMultiBlockMode())
			return CSAAdminEmployeeContext.getData();
		else
			return EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	}
}
