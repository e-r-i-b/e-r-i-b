package com.rssl.phizic.web.ext.sbrf;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author egorova
 * @ created 25.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentViewUtil extends com.rssl.phizic.web.util.DepartmentViewUtil
{
	private static final DepartmentService departmentService = new DepartmentService();
	/**
	 * @return Отключена ли выдача кредитных карт для департамента
	 * @param department департамент
	 */
	public static Boolean isCreditCardOfficeEditDisable(ExtendedDepartment department)
	{
		ExtendedCodeImpl code = (ExtendedCodeImpl)department.getCode();
		if (code != null && (StringHelper.isEmpty(code.getOffice())||StringHelper.equals(code.getOffice(),"0")))
			return true;
		return false;
	}

	/**
	 * Является ли офис ВСП
	 * @param office офис
	 * @return true - ВСП
	 */
	public static boolean isVSPOffice(Office office)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(office.getCode());

		return StringHelper.isNotEmpty(code.getRegion()) && StringHelper.isNotEmpty(code.getBranch())
				&& StringHelper.isNotEmpty(code.getOffice());
	}

	/**
	 * Существует ли офис с такими ТБ ОСБ ВСП
	 * @param cardLink линк
	 * @return true - существует
	 */
	public static boolean isVSPExist(CardLink cardLink) throws BusinessException
	{
		try
		{
			return departmentService.getDepartment(cardLink.getGflTB(), cardLink.getGflOSB(), cardLink.getGflVSP()) != null;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения департамента. ", e);
			return false;
		}
	}

	/**
	 * Является ли офис TB
	 * @param office офис
	 * @return true - ВСП
	 */
	public static boolean isTB(Office office)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(office.getCode());

		return StringHelper.isNotEmpty(code.getRegion()) && StringHelper.isEmpty(code.getBranch()) && StringHelper.isEmpty(code.getOffice());
	}

	/**
	 * Получить ТБ для указанного подразделения
	 * @param department подразделение
	 * @return ТБ
	 */
	public static Department getTB(Department department)
	{
		try
		{
			if(DepartmentService.isTB(department))
				return null;

			return departmentService.getTB(department);
		}
		catch (Exception e)
		{
			log.error("Ошибка при определении ТБ у подразделения с id = " + department.getId(), e);
			return null;
		}
	}
}
