package com.rssl.phizic.operations.registration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.connectUdbo.MultiInstanceUDBOClaimRulesService;
import com.rssl.phizic.business.connectUdbo.UDBOClaimRules;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

import java.util.List;
import java.util.Set;

/**
 * настройка подключения УДБО
 * @author basharin
 * @ created 30.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConnectUDBOSettingsOperation extends EditPropertiesOperation<Restriction>
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final MultiInstanceUDBOClaimRulesService claimRulesService = new MultiInstanceUDBOClaimRulesService();
	private List<Department> departments;
	private List<UDBOClaimRules> claimRulesList;

	@Override
	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		String instanceName = MultiBlockModeDictionaryHelper.getDBInstanceName();
		departments = departmentService.getTerbanks(instanceName);
		claimRulesList = claimRulesService.getRulesList(instanceName);
	}

	@Override
	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		String instanceName = MultiBlockModeDictionaryHelper.getDBInstanceName();
		departments = departmentService.getTerbanks(instanceName);
		claimRulesList = claimRulesService.getRulesList(instanceName);
	}

	public List<Department> getDepartments()
	{
		return departments;
	}

	/**
	 * @return список условий для заявления о подключении УДБО
	 */
	public List<UDBOClaimRules> getClaimRulesList()
	{
		return claimRulesList;
	}
}
