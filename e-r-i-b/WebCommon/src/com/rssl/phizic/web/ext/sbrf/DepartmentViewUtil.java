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
	 * @return ��������� �� ������ ��������� ���� ��� ������������
	 * @param department �����������
	 */
	public static Boolean isCreditCardOfficeEditDisable(ExtendedDepartment department)
	{
		ExtendedCodeImpl code = (ExtendedCodeImpl)department.getCode();
		if (code != null && (StringHelper.isEmpty(code.getOffice())||StringHelper.equals(code.getOffice(),"0")))
			return true;
		return false;
	}

	/**
	 * �������� �� ���� ���
	 * @param office ����
	 * @return true - ���
	 */
	public static boolean isVSPOffice(Office office)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(office.getCode());

		return StringHelper.isNotEmpty(code.getRegion()) && StringHelper.isNotEmpty(code.getBranch())
				&& StringHelper.isNotEmpty(code.getOffice());
	}

	/**
	 * ���������� �� ���� � ������ �� ��� ���
	 * @param cardLink ����
	 * @return true - ����������
	 */
	public static boolean isVSPExist(CardLink cardLink) throws BusinessException
	{
		try
		{
			return departmentService.getDepartment(cardLink.getGflTB(), cardLink.getGflOSB(), cardLink.getGflVSP()) != null;
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������������. ", e);
			return false;
		}
	}

	/**
	 * �������� �� ���� TB
	 * @param office ����
	 * @return true - ���
	 */
	public static boolean isTB(Office office)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(office.getCode());

		return StringHelper.isNotEmpty(code.getRegion()) && StringHelper.isEmpty(code.getBranch()) && StringHelper.isEmpty(code.getOffice());
	}

	/**
	 * �������� �� ��� ���������� �������������
	 * @param department �������������
	 * @return ��
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
			log.error("������ ��� ����������� �� � ������������� � id = " + department.getId(), e);
			return null;
		}
	}
}
