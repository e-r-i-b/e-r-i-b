package com.rssl.phizic.operations.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.departments.ListOfficesOperation;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class GetIMAOfficeListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final DepartmentService departmentService = new DepartmentService();
	private Department osb;

	/**
	 * ������������� ��������
	 * @param synchKey -  ���� ������������
	 */
	public void initialize(String synchKey) throws BusinessException, BusinessLogicException
	{
		Department department = departmentService.findBySynchKey(synchKey);
		if (department == null)
		{
			throw new BusinessException("�� ������� ������������� �� ���� " + synchKey);
		}

		osb = departmentService.getOSBByOffice(department);
		if (osb == null)
		{
			throw new BusinessException("�� ������� ��� ��� ������������� " + synchKey);
		}
	}

	/**
	 * @return ��� �� ���������� ������������
	 */
	@QueryParameter
	public String getTb()
	{
		return osb.getRegion();
	}

	/**
	 * @return ��� ��� ���������� ������������
	 */
	@QueryParameter
	public String getOsb()
	{
		return osb.getOSB();
	}
}
