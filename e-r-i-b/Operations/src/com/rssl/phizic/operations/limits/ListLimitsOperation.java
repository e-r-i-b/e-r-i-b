package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.operations.dictionaries.synchronization.ListDictionaryEntityOperationBase;

import java.util.List;

/**
 * @author gulov
 * @ created 19.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class ListLimitsOperation extends ListDictionaryEntityOperationBase<DepartmentRestriction>
{
	private static final String RESTRICTION_DEPARTMENT_MESSAGE = "Подразделение с идентификатором %d не является доступным для текущего пользователя";
	private static final GroupRiskService groupRiskService = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();


	/**
	 * Уникальный код подразделения
	 */
	private Department department;
    private boolean isAlloverLimitOperation;

	public void initialize(Long departmentId) throws BusinessException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException(String.format(RESTRICTION_DEPARTMENT_MESSAGE, departmentId));
		this.department = departmentService.findById(departmentId, getInstanceName());
        isAlloverLimitOperation = false;
	}

    public void initialize() {
        isAlloverLimitOperation = true;
    }

	/**
	 * @return номер ТБ
	 */
	public String getDepartmentId()
	{
		return isAlloverLimitOperation ? "888" : department == null ? null : department.getRegion();
	}

	//группы риска не привязаны к подразделению, получаем все группы риска
	public List<GroupRisk> getAllGroupRisk() throws BusinessException
	{
		return groupRiskService.getAllGroupsRisk(getInstanceName());
	}
}