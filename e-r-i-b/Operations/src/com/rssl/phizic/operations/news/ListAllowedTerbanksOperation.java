package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;
import com.rssl.phizic.security.PermissionUtil;

import java.util.List;

/**
 * @author lukina
 * @ created 15.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListAllowedTerbanksOperation extends ListDictionaryEntityOperationBase
{

	/**
	 * @return ������ ��������� ��������� ����������
	 * @throws BusinessException
	 */
	public List<Department> getTerbanks() throws BusinessException
	{
		//��� ������� � ������ ������ ������ �������� �� ��� ������ ����������� �� ��, � �������������� ������� ��������� ����� ������.
		if(PermissionUtil.impliesServiceRigid("SeachClientsByTB"))
			return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();

		return AllowedDepartmentsUtil.getAllowedTerbanks();
	}

}
