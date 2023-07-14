package com.rssl.phizic.operations.registration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Настройки СБНКД
 * @author basharin
 * @ created 08.01.15
 * @ $Author$
 * @ $Revision$
 */

public class SBNKDSettingsOperation extends EditPropertiesOperation<Restriction>
{
	private static final DepartmentService departmentService = new DepartmentService();
	private List<Department> departments;

	@Override
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		departments = departmentService.getTerbanks(MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		departments = departmentService.getTerbanks(MultiBlockModeDictionaryHelper.getDBInstanceName());
	}

	public List<Department> getDepartments()
	{
		CollectionUtils.filter(departments, new TBFilter(getEntity().get("com.rssl.iccs.sbnkd.allowedTB")));
		return departments;
	}
}
