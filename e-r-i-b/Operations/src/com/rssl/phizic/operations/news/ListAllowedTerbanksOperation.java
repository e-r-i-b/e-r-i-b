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
	 * @return Список тербанков доступных сотруднику
	 * @throws BusinessException
	 */
	public List<Department> getTerbanks() throws BusinessException
	{
		//При наличии в правах пункта «Поиск клиентов по ТБ» должны отобразится те ТБ, к подразделениям которых сотрудник имеет доступ.
		if(PermissionUtil.impliesServiceRigid("SeachClientsByTB"))
			return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();

		return AllowedDepartmentsUtil.getAllowedTerbanks();
	}

}
