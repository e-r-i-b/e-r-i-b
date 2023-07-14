package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.operations.autosubscription.AutoSubscriptionOperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bogdanov
 * @ created 22.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ����������� ���������� �� �������� �������.
 */

public class ShowPersonInfoOperation extends AutoSubscriptionOperationBase
{
	private static DepartmentService departmentService = new DepartmentService();

	/**
	 * �������������, � ������� ��������������� ������������.
	 */
	private Department userDepartment;

	@Override 
	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		userDepartment = departmentService.findById(getPerson().getDepartmentId());
	}

	/**
	 * @return ������ ���������� �������
	 */
	public List<String> getDocumentTypes()
	{
		List<String> types = new ArrayList<String>(PersonDocumentType.values().length);
		for (PersonDocumentType type : PersonDocumentType.values())
		{
			types.add(type.name());
		}
		return types;
	}

	/**
	 * @return �������������, � ������� ��������������� ������������.
	 */
	public Department getUserDepartment()
	{
		return userDepartment;
	}
}
